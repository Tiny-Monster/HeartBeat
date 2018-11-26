package com.tinymonster.heartbeat3.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.activity.BBSActivity;
import com.tinymonster.heartbeat3.activity.BBSDetailActivity;
import com.tinymonster.heartbeat3.activity.WriteArticleActivity;
import com.tinymonster.heartbeat3.entity.BBSArticle;

import java.util.List;

/**
 * Created by TinyMonster on 15/08/2018.
 */

public class article_item_adapter extends RecyclerView.Adapter<article_item_adapter.ViewHolder>{
    private List<BBSArticle> list;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView bbs_name_tv;
        TextView bbs_title_tv;
        TextView bbs_msg_tv;
        TextView bbs_time;
        CardView bbs_article_item;
        public ViewHolder(View view){
            super(view);
            bbs_name_tv = (TextView)view.findViewById(R.id.bbs_name_tv);
            bbs_title_tv=(TextView)view.findViewById(R.id.bbs_title_tv);
            bbs_msg_tv=(TextView)view.findViewById(R.id.bbs_msg_tv);
            bbs_time=(TextView)view.findViewById(R.id.bbs_time);
            bbs_article_item=(CardView)view.findViewById(R.id.bbs_article_item);
        }
    }

    public article_item_adapter(List<BBSArticle> list){
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msgitem,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BBSArticle bbsArticle=list.get(position);
        holder.bbs_name_tv.setText(bbsArticle.getUserName());
        holder.bbs_title_tv.setText(bbsArticle.getTitle());
        holder.bbs_time.setText(bbsArticle.getTime());
        holder.bbs_msg_tv.setText(bbsArticle.getMessage());
        holder.bbs_article_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), BBSDetailActivity.class);
                intent.putExtra("key",bbsArticle.getKey());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
