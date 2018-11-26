package com.tinymonster.heartbeat3.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiUser;
import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.adapter.comment_item_adapter;
import com.tinymonster.heartbeat3.entity.BBSArticle;
import com.tinymonster.heartbeat3.entity.BBSComment;

import java.util.ArrayList;
import java.util.List;

public class BBSDetailActivity extends AppCompatActivity {
    public String Key;
    private ImageView bbs_detail_img_return;
    private TextView bbs_detail_name_tv;
    private TextView bbs_detail_time;
    private TextView bbs_detail_title_tv;
    private TextView bbs_detail_msg_tv;
    private RecyclerView bbs_detail_RecycleView;
    private EditText bbs_detail_send_et;
    private BBSArticle Now_BBSArticle;
    private List<BBSComment> commentList=new ArrayList<>();
    private comment_item_adapter My_comment_item_adapter;
    private SwipeRefreshLayout bbs_detail_fresh;
    private ImageView bbs_detail_send_iv;
    private static final int DOWNLOAD_SUCCESS=3;
    private static final int DOWNLOAD_FAIL=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbsdetail);
        Intent intent=getIntent();
        Key=intent.getStringExtra("key");
        Now_BBSArticle=getArticle(Key);
        initView();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        My_comment_item_adapter=new comment_item_adapter(commentList);
        bbs_detail_RecycleView.setLayoutManager(linearLayoutManager);
        bbs_detail_RecycleView.setAdapter(My_comment_item_adapter);
    }
    private void initView(){
        bbs_detail_send_iv=(ImageView)findViewById(R.id.bbs_detail_send_iv);
        bbs_detail_fresh=(SwipeRefreshLayout)findViewById(R.id.bbs_detail_fresh);
        bbs_detail_img_return=(ImageView)findViewById(R.id.bbs_detail_img_return);
        bbs_detail_name_tv=(TextView)findViewById(R.id.bbs_detail_name_tv);
        bbs_detail_time=(TextView)findViewById(R.id.bbs_detail_time);
        bbs_detail_title_tv=(TextView)findViewById(R.id.bbs_detail_title_tv);
        bbs_detail_msg_tv=(TextView)findViewById(R.id.bbs_detail_msg_tv);
        bbs_detail_RecycleView=(RecyclerView)findViewById(R.id.bbs_detail_RecycleView);
        bbs_detail_send_et=(EditText)findViewById(R.id.bbs_detail_send_et);
        //初始化信息
        bbs_detail_name_tv.setText(Now_BBSArticle.getUserName());
        bbs_detail_time.setText(Now_BBSArticle.getTime());
        bbs_detail_title_tv.setText(Now_BBSArticle.getTitle());
        bbs_detail_msg_tv.setText(Now_BBSArticle.getMessage());
        bbs_detail_fresh.setEnabled(true);
        bbs_detail_fresh.setOnRefreshListener(onRefreshListener);
        bbs_detail_img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bbs_detail_send_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComment(bbs_detail_send_et.getText().toString());
                Log.e("BBSDETAIL","点击发送");
            }
        });
    }
    private BBSArticle getArticle(String key){
        DroiCondition condition=DroiCondition.cond("key",DroiCondition.Type.EQ,key);
        DroiQuery query=DroiQuery.Builder.newBuilder().query(BBSArticle.class).where(condition)
                .orderBy("date",false).build();
        DroiError error=new DroiError();
        List<BBSArticle> result=query.runQuery(error);
        if(error.isOk()&&result.size()>0){
            return result.get(0);
        }else if(!(result.size()>0)){
            Toast.makeText(BBSDetailActivity.this,"暂无评论",Toast.LENGTH_SHORT).show();
            return null;
        }else {
            Toast.makeText(BBSDetailActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public void downloadComment(){
        DroiCondition condition=DroiCondition.cond("key",DroiCondition.Type.EQ,Key);
        DroiQuery query=DroiQuery.Builder.newBuilder().query(BBSComment.class).where(condition)
                .orderBy("date",true).build();
        DroiError error=new DroiError();
        List<BBSComment> results=query.runQuery(error);
        if(error.isOk()&&results.size()>0){
            commentList.clear();
            commentList.addAll(results);
            My_comment_item_adapter.notifyDataSetChanged();
            bbs_detail_fresh.setRefreshing(false);
        }else {
            bbs_detail_fresh.setRefreshing(false);
            Toast.makeText(BBSDetailActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
        }
    }
    SwipeRefreshLayout.OnRefreshListener onRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Log.e("BBSActivity","BBS_FRESH开始刷新");
            downloadComment();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        bbs_detail_fresh.setRefreshing(true);
        onRefreshListener.onRefresh();
    }
    private void saveComment(String msg){
        String UserName=BBSActivity.USERNAME;
        BBSComment bbsComment=new BBSComment(Key,UserName,msg,"null");
        DroiPermission permission = new DroiPermission();
        permission.setPublicReadPermission(true);
        permission.setUserReadPermission(DroiUser.getCurrentUser().getObjectId(), true);
        permission.setUserWritePermission(DroiUser.getCurrentUser().getObjectId(), true);
        bbsComment.setPermission(permission);
        bbsComment.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if(aBoolean){
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
                case DOWNLOAD_SUCCESS://正确
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BBSDetailActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                            bbs_detail_fresh.setRefreshing(true);
                            onRefreshListener.onRefresh();
                        }
                    });
                    break;
                case DOWNLOAD_FAIL://错误
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(BBSDetailActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };
}
