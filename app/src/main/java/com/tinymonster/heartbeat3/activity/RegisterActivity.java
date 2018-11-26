package com.tinymonster.heartbeat3.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.tinymonster.heartbeat3.util.HttpUtil.JSON;

public class RegisterActivity extends AppCompatActivity {
    private EditText register_Edt_phone;
    private EditText register_Edt_password;
    private EditText register_Edt_password_verify;
    private Button register;
    private ProgressDialog progressDialog;
    private ImageView register_img_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView(){
        register_img_return=(ImageView)findViewById(R.id.register_img_return);
        register_Edt_phone=(EditText)findViewById(R.id.register_Edt_phone);
        register_Edt_password=(EditText)findViewById(R.id.register_Edt_password);
        register_Edt_password_verify=(EditText)findViewById(R.id.register_Edt_password_verify);
        register=(Button)findViewById(R.id.register);
        register_img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=register_Edt_phone.getText().toString();
                String pwd=register_Edt_password.getText().toString();
                final String pwd_confirm=register_Edt_password_verify.getText().toString();
                if(name==""||pwd==""||pwd_confirm==""){
                    Toast.makeText(RegisterActivity.this,"输入信息不能为空",Toast.LENGTH_SHORT).show();
                }else if(!pwd.equals(pwd_confirm)) {
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("userName", name);
                        jsonObject.put("userPassword", pwd_confirm);
                        jsonObject.put("userSex", "M");
                        jsonObject.put("userAge", 50);
                    }catch(JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
                    HttpUtil.sendOkHttpPost("http://39.105.104.164/heartbeat/register", requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result=response.body().string();
                            if(result.equals("true")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }else if(result.equals("false")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this,"您的账号已注册",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
