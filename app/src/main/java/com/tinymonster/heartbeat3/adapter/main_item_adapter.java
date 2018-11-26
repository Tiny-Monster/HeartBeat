package com.tinymonster.heartbeat3.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.activity.MainActivity;
import com.tinymonster.heartbeat3.activity.MoreInfoActivity;
import com.tinymonster.heartbeat3.activity.StartActivity;
import com.tinymonster.heartbeat3.entity.JsonRate;
import com.tinymonster.heartbeat3.entity.entity_device;
import com.tinymonster.heartbeat3.util.ColorUtil;
import com.tinymonster.heartbeat3.util.HttpUtil;
import com.tinymonster.heartbeat3.view.StepArcView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by TinyMonster on 30/06/2018.
 */

public class main_item_adapter extends RecyclerView.Adapter<main_item_adapter.ViewHolder>{
    List<entity_device> Device_list;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView main_fan_item_textview;
        CardView main_fan_item_cardview;
        ImageView main_fan_item_delete;
        public ViewHolder(View view) {
            super(view);
            main_fan_item_textview=(TextView)view.findViewById(R.id.main_fan_item_textview);
            main_fan_item_cardview=(CardView)view.findViewById(R.id.main_fan_item_cardview);
            main_fan_item_delete=(ImageView)view.findViewById(R.id.main_fan_item_delete_ImageView);
        }
    }
    public main_item_adapter(List<entity_device> device_list){
        this.Device_list=device_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fan_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.main_fan_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("item_adapter","点击");
                int position=holder.getAdapterPosition();
                final entity_device device=Device_list.get(position);
                Device_list.remove(position);
                StartActivity.deleteDeviceById(device.getId());
                notifyItemChanged(position);
                notifyDataSetChanged();
                ImageView  main_info_video=(ImageView)parent.getRootView().findViewById(R.id.main_info_video);
                TextView name=(TextView) parent.getRootView().findViewById(R.id.main_info_name_TextView);
                TextView sex=(TextView) parent.getRootView().findViewById(R.id.main_info_sex_TextView);
                StepArcView stepArcView=(StepArcView)parent.getRootView().findViewById(R.id.main_info_StepArcView);
                ImageView more_info=(ImageView)parent.getRootView().findViewById(R.id.main_info_moreInfo_ImageView);
                more_info.setVisibility(View.INVISIBLE);
                main_info_video.setVisibility(View.INVISIBLE);
                name.setText("未知");
                sex.setText("未知");
                stepArcView.setCurrentCount(180,0);
            }
        });
        holder.main_fan_item_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                弹出对话框
                 */
                Log.e("item_adapter","点击");
                int position=holder.getAdapterPosition();
                final entity_device device=Device_list.get(position);
                TextView name=(TextView) parent.getRootView().findViewById(R.id.main_info_name_TextView);
                TextView sex=(TextView) parent.getRootView().findViewById(R.id.main_info_sex_TextView);
                ImageView main_info_video=(ImageView)parent.getRootView().findViewById(R.id.main_info_video);
                StepArcView stepArcView=(StepArcView)parent.getRootView().findViewById(R.id.main_info_StepArcView);
                ImageView more_info=(ImageView)parent.getRootView().findViewById(R.id.main_info_moreInfo_ImageView);
                main_info_video.setVisibility(View.VISIBLE);
                more_info.setVisibility(View.VISIBLE);
                name.setText(device.getNick_name());
                sex.setText(device.getSex());
                if(device.getHeart_rate_now().equals("暂无数据")){
                    stepArcView.setCurrentCount(180,0);
                }else {
                    stepArcView.setCurrentCount(180,Integer.valueOf(device.getHeart_rate_now()));
                }
                Intent intent=new Intent();
                intent.setAction(MainActivity.REFRESHRATE);
                intent.putExtra("DEVICENAME",device.getDevice_name());
                intent.putExtra("NICKNAME",device.getNick_name());
                MainActivity.main_context.sendBroadcast(intent);
                more_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MainActivity.main_context, MoreInfoActivity.class);
                        intent.putExtra("device_name",device.getDevice_name());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.main_context.startActivity(intent);
                    }
                });
//                main_info_video.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //视频
//
//                    }
//                });
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        entity_device device=Device_list.get(position);
        holder.main_fan_item_textview.setText(device.getNick_name());
        holder.main_fan_item_cardview.setCardBackgroundColor(ColorUtil.ColorArray[(int)(device.getId()%6)]);
    }

    @Override
    public int getItemCount() {
        return Device_list.size();
    }
}
