package com.tinymonster.heartbeat3.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.entity.BBSComment;

import java.util.List;

/**
 * Created by TinyMonster on 16/08/2018.
 */

public class comment_item_adapter extends RecyclerView.Adapter<comment_item_adapter.ViewHolder>{
    private List<BBSComment> commentList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView bbs_comment_name_tv;
        private TextView bbs_comment_time;
        private TextView bbs_comment_msg_tv;
        public ViewHolder(View view){
            super(view);
            bbs_comment_name_tv=(TextView)view.findViewById(R.id.bbs_comment_name_tv);
            bbs_comment_time=(TextView)view.findViewById(R.id.bbs_comment_time);
            bbs_comment_msg_tv=(TextView)view.findViewById(R.id.bbs_comment_msg_tv);
        }
    }
    public comment_item_adapter(List<BBSComment> comments){
        this.commentList=comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bbs_comment_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BBSComment bbsComment=commentList.get(position);
        holder.bbs_comment_msg_tv.setText(bbsComment.getMessage());
        holder.bbs_comment_name_tv.setText(bbsComment.getName());
        holder.bbs_comment_time.setText(bbsComment.getTime());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
