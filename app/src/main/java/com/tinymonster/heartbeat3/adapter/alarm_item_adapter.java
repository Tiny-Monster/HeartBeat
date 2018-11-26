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

import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.activity.MainActivity;
import com.tinymonster.heartbeat3.activity.MoreInfoActivity;
import com.tinymonster.heartbeat3.activity.StartActivity;
import com.tinymonster.heartbeat3.entity.entity_alarm;
import com.tinymonster.heartbeat3.entity.entity_device;
import com.tinymonster.heartbeat3.util.ColorUtil;
import com.tinymonster.heartbeat3.view.StepArcView;

import java.util.List;

/**
 * Created by TinyMonster on 03/07/2018.
 */

public class alarm_item_adapter extends RecyclerView.Adapter<alarm_item_adapter.ViewHolder>{
    List<entity_alarm> alarm_list;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView alarm_item_name;
        TextView alarm_item_time;
        TextView alarm_item_msg;
        ImageView alarm_item_delete;
        CardView alarm_item_cardView;
        public ViewHolder(View view) {
            super(view);
            alarm_item_name=(TextView)view.findViewById(R.id.alarm_item_name);
            alarm_item_time=(TextView) view.findViewById(R.id.alarm_item_time);
            alarm_item_msg=(TextView) view.findViewById(R.id.alarm_item_msg);
            alarm_item_delete=(ImageView)view.findViewById(R.id.alarm_item_delete);
            alarm_item_cardView=(CardView)view.findViewById(R.id.alarm_item_cardView);
        }
    }
    public alarm_item_adapter(List<entity_alarm> list){
        this.alarm_list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item,parent,false);
        final alarm_item_adapter.ViewHolder holder=new alarm_item_adapter.ViewHolder(view);
        holder.alarm_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                final entity_alarm alarm=alarm_list.get(position);
                StartActivity.deleteAlarmByAlarmId(alarm.getAlarmID());
                alarm_list.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(alarm_item_adapter.ViewHolder holder, int position) {
        entity_alarm alarm=alarm_list.get(position);
        String name=alarm.getName()+" : ";
        String time=alarm.getHour()+"时"+alarm.getMinute()+"分";
        holder.alarm_item_msg.setText(alarm.getMessage());
        holder.alarm_item_name.setText(name);
        holder.alarm_item_time.setText(time);
        holder.alarm_item_cardView.setCardBackgroundColor(ColorUtil.ColorArray[(int)(alarm.getId()%6)]);
    }

    @Override
    public int getItemCount() {
        return alarm_list.size();
    }
    public void add(entity_alarm alarm){
        alarm_list.add(alarm);
        notifyItemInserted(alarm_list.size());
        notifyDataSetChanged();
    }
}
