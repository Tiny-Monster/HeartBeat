package com.tinymonster.heartbeat3.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.adapter.article_item_adapter;
import com.tinymonster.heartbeat3.entity.BBSArticle;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class BBSActivity extends AppCompatActivity{
    private ImageView bbs_img_return;
    private ImageView bbs_img_News;
    private RecyclerView bbs_RecycleView;
    private ImageView bbs_article_write;
    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;
    private static final int DOWNLOAD_SUCCESS=3;
    private static final int DOWNLOAD_FAIL=4;
    public static String USERNAME;
    private SwipeRefreshLayout BBS_FRESH;
    article_item_adapter My_article_item_adapter;
    private List<BBSArticle> ArticleList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs);
        initView();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        bbs_RecycleView.setLayoutManager(linearLayoutManager);
        My_article_item_adapter=new article_item_adapter(ArticleList);
        bbs_RecycleView.setAdapter(My_article_item_adapter);
        BBS_FRESH.setEnabled(true);
        Log.e("BBSActivity","初始化");
//        BBS_FRESH.setRefreshing(true);
    }
    private void initView(){
        BBS_FRESH=(SwipeRefreshLayout)findViewById(R.id.BBS_FRESH);
        bbs_img_return=(ImageView)findViewById(R.id.bbs_img_return);
        bbs_img_News=(ImageView)findViewById(R.id.bbs_img_News);
        bbs_RecycleView=(RecyclerView) findViewById(R.id.bbs_RecycleView);
        bbs_article_write=(ImageView) findViewById(R.id.bbs_article_write);
        preferences= PreferenceManager.getDefaultSharedPreferences(BBSActivity.this);
        USERNAME=preferences.getString("name","未知");
        /////////////////////////////////////////////////////////////////
        BBS_FRESH.setOnRefreshListener(onRefreshListener);
        bbs_img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bbs_img_News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        bbs_article_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BBSActivity.this,WriteArticleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void download(){
        Log.e("BBSActivity","开始下载");
        DroiQuery query=DroiQuery.Builder.newBuilder().cloudStorage().query(BBSArticle.class).orderBy("date",false).build();
        query.runQueryInBackground(new DroiQueryCallback<BBSArticle>() {
            @Override
            public void result(List<BBSArticle> list, DroiError droiError) {
                if(droiError.isOk()&&list.size()>0){
                    ArticleList.clear();
                    ArticleList.addAll(list);
                    Log.e("BBSActivity","返回成功");
                    mHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
                }else {
                    mHandler.sendEmptyMessage(DOWNLOAD_FAIL);
                }
            }
        });
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_SUCCESS://去主页
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BBSActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                            BBS_FRESH.setRefreshing(false);
                            My_article_item_adapter.notifyDataSetChanged();
                        }
                    });
                    break;
                case DOWNLOAD_FAIL://去登录页
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BBS_FRESH.setRefreshing(false);
                            Toast.makeText(BBSActivity.this,"刷新失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        BBS_FRESH.post(new Runnable() {
            @Override
            public void run() {
                BBS_FRESH.setRefreshing(true);
                onRefreshListener.onRefresh();
            }
        });
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Log.e("BBSActivity","BBS_FRESH开始刷新");
            download();
        }
    };
}
