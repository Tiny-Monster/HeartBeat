package com.tinymonster.heartbeat3.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.core.DroiUser;
import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.entity.BBSArticle;

public class WriteArticleActivity extends AppCompatActivity {
    private ImageView write_article_img_return;
    private EditText write_article_title;
    private EditText write_article_msg;
    private ImageView write_article_ok;
    private ImageView write_article_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_article);
        initView();
    }
    private void initView(){
        write_article_img_return=(ImageView)findViewById(R.id.write_article_img_return);
        write_article_title=(EditText)findViewById(R.id.write_article_title);
        write_article_msg=(EditText)findViewById(R.id.write_article_msg);
        write_article_ok=(ImageView)findViewById(R.id.write_article_ok);
        write_article_delete=(ImageView)findViewById(R.id.write_article_delete);
        write_article_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        write_article_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DroiPermission permission = new DroiPermission();
                permission.setPublicReadPermission(true);
                permission.setUserReadPermission(DroiUser.getCurrentUser().getObjectId(), true);
                permission.setUserWritePermission(DroiUser.getCurrentUser().getObjectId(), true);
                BBSArticle bbsArticle=new BBSArticle(BBSActivity.USERNAME,write_article_title.getText().toString(),write_article_msg.getText().toString(),
                        "null");
                bbsArticle.setPermission(permission);
                bbsArticle.saveInBackground(new DroiCallback<Boolean>() {
                    @Override
                    public void result(Boolean aBoolean, DroiError droiError) {
                        if(aBoolean){
                            Toast.makeText(WriteArticleActivity.this,"发帖成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(WriteArticleActivity.this,BBSActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(WriteArticleActivity.this,"发帖失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
