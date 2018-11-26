package com.tinymonster.heartbeat3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WebNewsActivity extends AppCompatActivity {
    private ImageView news_img_return;
    private ImageView news_img_fresh;
    private WebView new_webView;
    private String TempUrl;
    private String url="http://39.105.104.164/FaceBeat/news";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_news);
        initView();
        new_webView.getSettings().setJavaScriptEnabled(true);
        new_webView.setWebViewClient(new WebViewClient());
        fresh();
    }
    private void initView(){
        news_img_return=(ImageView)findViewById(R.id.news_img_return);
        news_img_fresh=(ImageView)findViewById(R.id.news_img_fresh);
        new_webView=(WebView)findViewById(R.id.new_webView);
        news_img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        news_img_fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fresh();
            }
        });
    }
    private void fresh(){
        HttpUtil.sendOkHttpGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WebNewsActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TempUrl=response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("news",TempUrl);
                        new_webView.loadUrl(TempUrl);
                    }
                });
            }
        });
    }

}
