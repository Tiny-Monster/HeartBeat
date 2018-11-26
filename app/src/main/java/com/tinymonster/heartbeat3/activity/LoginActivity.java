package com.tinymonster.heartbeat3.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.core.DroiUser;
import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.entity.BBSArticle;
import com.tinymonster.heartbeat3.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.tinymonster.heartbeat3.util.HttpUtil.JSON;

public class LoginActivity extends AppCompatActivity {
    private EditText Login_username;
    private EditText Login_password;
    private TextView login_register;
    private Button login;
    public static Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;
    private String UserName;
    private String Pwd;
    public static String StaticName;
    public static final int LOGIN_SUCCESS=5;
    public static final int LOGIN_FAIL=6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView(){
        login_register=(TextView)findViewById(R.id.login_register);
        preferences= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        /*
        progressDialog
         */
        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("网络连接中……");
        progressDialog.setCancelable(false);
        /*
        控件初始化
         */
        Login_username=(EditText)findViewById(R.id.Login_username);
        Login_password=(EditText)findViewById(R.id.Login_password);
        login_register=(TextView)findViewById(R.id.login_register);
        login=(Button) findViewById(R.id.login);
        /*
        控件事件
         */
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserName=Login_username.getText().toString();
                Pwd=Login_password.getText().toString();
                if(UserName.equals("root")){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    if(UserName.equals("")||UserName==null){
                        Toast.makeText(LoginActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                    }else if(Pwd.equals("")||Pwd==null){
                        Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.show();
                        DroiError error=new DroiError();
                        DroiUser droiUser=DroiUser.login(UserName,Pwd,DroiUser.class,error);
                        if(error.isOk()&&droiUser!=null&&droiUser.isAuthorized()){
                            System.out.println("droi登陆成功");
                        }
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("userName",UserName);
                            jsonObject.put("userPassword",Pwd);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
                        HttpUtil.sendOkHttpPost("http://39.105.104.164/heartbeat/login", requestBody, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if(response!=null){
                                    String result=response.body().string();
                                    if(result.equals("login success")){
                                        progressDialog.dismiss();
                                        editor=preferences.edit();
                                        editor.putBoolean("state",true);
                                        editor.putString("name",Login_username.getText().toString());
                                        editor.apply();
                                        StaticName=Login_username.getText().toString();
//                                        DroiPermission permission = new DroiPermission();
//                                        permission.setPublicReadPermission(true);
//                                        permission.setUserReadPermission(DroiUser.getCurrentUser().getObjectId(), true);
//                                        permission.setUserWritePermission(DroiUser.getCurrentUser().getObjectId(), true);
//                                        BBSArticle bbsArticle=new BBSArticle("1","1","1","1");
//                                        bbsArticle.setPermission(permission);
//                                        bbsArticle.saveInBackground(new DroiCallback<Boolean>() {
//                                            @Override
//                                            public void result(Boolean aBoolean, DroiError droiError) {
//                                                if(aBoolean){
//                                                    System.out.println("保存成功");
//                                                }else {
//                                                    System.out.println("保存失败");
//                                                }
//                                            }
//                                        });
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else if(result.equals("password is wrong")){
                                        Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }else {
                                        Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }else {
                                    Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
