package com.tinymonster.heartbeat3.activity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.adapter.alarm_item_adapter;
import com.tinymonster.heartbeat3.entity.entity_alarm;
import com.tinymonster.heartbeat3.refine.PickerView;
import com.tinymonster.heartbeat3.util.BitmapBlurUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AlarmActivity extends AppCompatActivity {
    private ImageView pillplan_img_add;
    private Dialog dialog;
    private Dialog alarm_display_dialog;
    private View dialog_view;
    private RecyclerView recyclerView;
    private List<entity_alarm> alarm_list;
    private static final String TAG="AlarmActivity";
    LayoutInflater inflater;
    List<String> hour = new ArrayList<String>();
    List<String> minute = new ArrayList<String>();
    private PickerView pickerView_hour;
    private PickerView pickerView_minute;
    private ImageView plan_add_ok;
    private ImageView plan_add_delete;
    private EditText plan_add_dialog_name;
    private EditText plan_add_dialog_msg;
    private AlarmReceiver alarmReceiver;
    private IntentFilter intentFilter;
    private PendingIntent pendingIntent;
    private MediaPlayer mediaPlayer;
    private static final int INTERVAL = 1000 * 60 * 60 * 24;// 24h
    /*
    闹钟
     */
    private String GetHour="12";
    private String GetMinute="30";
    private String name;
    private String msg;
    private static final String RING="com.tinymonster.heartbeat3.RING";
    private String RingId;
    private AlarmManager alarmManager;
    private Calendar calendar;
    LinearLayoutManager linearLayout;
    alarm_item_adapter adapter;
    /*
    闹钟显示
     */
    private TextView alarm_diaplay_dialog_name;
    private TextView alarm_diaplay_dialog_msg;
    private ImageView alarm_diaplay_mute;
    private ImageView alarm_diaplsy_finish;
    private ImageView pillplan_img_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        initDate();
        initView();
        initClickEvent();
        linearLayout=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        adapter=new alarm_item_adapter(alarm_list);
        recyclerView.setAdapter(adapter);
        alarmReceiver=new AlarmReceiver();
        intentFilter=new IntentFilter();
        intentFilter.addAction(RING);
        registerReceiver(alarmReceiver,intentFilter);
    }
    private void initView(){
        pillplan_img_return=(ImageView)findViewById(R.id.pillplan_img_return);
        recyclerView=(RecyclerView)findViewById(R.id.plan_recycleView);
        inflater=LayoutInflater.from(AlarmActivity.this);
        pillplan_img_add=(ImageView)findViewById(R.id.pillplan_img_add);
    }
    private void initDate(){
        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mediaPlayer=MediaPlayer.create(AlarmActivity.this,R.raw.alarm);
        mediaPlayer.setLooping(true);
        alarm_list=StartActivity.getAllAlarm();
        for (int i = 0; i < 10; i++) {
            hour.add("0" + i);
        }
        for (int i1 = 10; i1 < 24; i1++) {
            hour.add(Integer.toString(i1));
        }
        for (int i = 0; i < 60; i++) {
            minute.add(i < 10 ? "0" + i : "" + i);
        }
    }
    private void initClickEvent(){
        pillplan_img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pillplan_img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(AlarmActivity.this,R.style.SquareEntranceDialogStyle);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View layout = View.inflate(AlarmActivity.this, R.layout.plan_add_dialog, null);
                dialog.setContentView(layout);
                pickerView_hour=(PickerView)dialog.getWindow().findViewById(R.id.plan_add_dialog_hour);
                pickerView_minute=(PickerView)dialog.getWindow().findViewById(R.id.plan_add_dialog_minute);
                plan_add_ok=(ImageView)dialog.getWindow().findViewById(R.id.plan_dialog_ok);
                plan_add_delete=(ImageView)dialog.getWindow().findViewById(R.id.plan_dialog_delete);
                plan_add_dialog_name=(EditText)dialog.getWindow().findViewById(R.id.plan_add_dialog_name);
                plan_add_dialog_msg=(EditText)dialog.getWindow().findViewById(R.id.plan_add_dialog_msg);
                pickerView_hour.setData(hour);
                pickerView_minute.setData(minute);
                pickerView_hour.setOnSelectListener(new PickerView.onSelectListener() {
                    @Override
                    public void onSelect(String text) {
                        GetHour=text;
                    }
                });
                pickerView_minute.setOnSelectListener(new PickerView.onSelectListener() {
                    @Override
                    public void onSelect(String text) {
                        GetMinute=text;
                    }
                });
                plan_add_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name=plan_add_dialog_name.getText().toString();
                        msg=plan_add_dialog_msg.getText().toString();
                        //添加操作
                        calendar=Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(GetHour));
                        calendar.set(Calendar.MINUTE,Integer.valueOf(GetMinute));
                        calendar.set(Calendar.SECOND,0);
                        Log.e("AlarmActivity：获取的时间",calendar.getTime().toString());
                        String alarmID= String.valueOf(SystemClock.currentThreadTimeMillis());
                        Log.e(name,msg);
                        Log.e("RINGID",alarmID);
                        StartActivity.insertAlarm(name,msg,Integer.valueOf(GetHour),Integer.valueOf(GetMinute),alarmID);
                        Intent intent=new Intent();
                        intent.setAction(RING);
                        intent.putExtra("RingId",alarmID);
                        pendingIntent=PendingIntent.
                                getBroadcast(AlarmActivity.this,0,intent,0);//将来的INTENT
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                        Log.e("AlarmActicity","添加闹钟");
                        adapter.add(StartActivity.getLastAlarm());
                        dialog.dismiss();
                    }
                });
                plan_add_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                Window window = dialog.getWindow();
                dialog_view=inflater.inflate(R.layout.plan_add_dialog,null);
                Bitmap blurBg = null;
                if (window != null) {
                    long startMs = System.currentTimeMillis();
                    // 获取截图
                    View activityView = getWindow().getDecorView();
                    activityView.setDrawingCacheEnabled(true);
                    activityView.destroyDrawingCache();
                    activityView.buildDrawingCache();
                    Bitmap bmp = activityView.getDrawingCache();
                    Log.d(TAG, "getDrawingCache take away:" + (System.currentTimeMillis() - startMs) + "ms");
                    // 模糊处理并保存
                    blurBg = BitmapBlurUtil.blur(AlarmActivity.this, bmp);
                    Log.d(TAG, "blur take away:" + (System.currentTimeMillis() - startMs) + "ms");
                    // 设置成dialog的背景
                    window.setBackgroundDrawable(new BitmapDrawable(getResources(), blurBg));
                    bmp.recycle();
                }
                final Bitmap finalBlurBg = blurBg;
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // 对话框取消时释放背景图bitmap
                        if (finalBlurBg != null && !finalBlurBg.isRecycled()) {
                            finalBlurBg.recycle();
                        }
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
        });
    }
    class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("AlarmActivity","收到广播");
            if(intent.getAction().equals(RING)){
                RingId=intent.getStringExtra("RingId");
                alarmManager.cancel(pendingIntent);
                Log.e("收到的ID",RingId);
                entity_alarm entity_alarm=StartActivity.getAlarmByAlarmId(RingId);
                mediaPlayer=MediaPlayer.create(AlarmActivity.this,R.raw.alarm);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                Log.e("Alarm:msg",entity_alarm.getMessage());
                Log.e("Alarm:name",entity_alarm.getName());
                /*
                显示dialog
                    private TextView alarm_diaplay_dialog_name;
                    private TextView alarm_diaplay_dialog_sex;
                    private ImageView alarm_diaplay_mute;
                    private ImageView alarm_diaplsy_finish;
                 */
                alarm_display_dialog = new Dialog(AlarmActivity.this,R.style.SquareEntranceDialogStyle);
                alarm_display_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View layout = View.inflate(AlarmActivity.this, R.layout.alarm_display, null);
                alarm_display_dialog.setContentView(layout);
                alarm_diaplay_dialog_name=(TextView)alarm_display_dialog.getWindow().findViewById(R.id.alarm_diaplay_dialog_name);
                alarm_diaplay_dialog_msg=(TextView)alarm_display_dialog.getWindow().findViewById(R.id.alarm_diaplay_dialog_msg);
                alarm_diaplay_mute=(ImageView)alarm_display_dialog.getWindow().findViewById(R.id.alarm_diaplay_mute);
                alarm_diaplsy_finish=(ImageView)alarm_display_dialog.getWindow().findViewById(R.id.alarm_diaplsy_finish);
                alarm_diaplay_dialog_name.setText(entity_alarm.getName());
                alarm_diaplay_dialog_msg.setText(entity_alarm.getMessage());
                alarm_diaplay_mute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mediaPlayer.stop();
                    }
                });
                alarm_diaplsy_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alarm_display_dialog.dismiss();
                    }
                });
                Window window = alarm_display_dialog.getWindow();
                dialog_view=inflater.inflate(R.layout.plan_add_dialog,null);
                Bitmap blurBg = null;
                if (window != null) {
                    long startMs = System.currentTimeMillis();
                    // 获取截图
                    View activityView = getWindow().getDecorView();
                    activityView.setDrawingCacheEnabled(true);
                    activityView.destroyDrawingCache();
                    activityView.buildDrawingCache();
                    Bitmap bmp = activityView.getDrawingCache();
                    Log.d(TAG, "getDrawingCache take away:" + (System.currentTimeMillis() - startMs) + "ms");
                    // 模糊处理并保存
                    blurBg = BitmapBlurUtil.blur(AlarmActivity.this, bmp);
                    Log.d(TAG, "blur take away:" + (System.currentTimeMillis() - startMs) + "ms");
                    // 设置成dialog的背景
                    window.setBackgroundDrawable(new BitmapDrawable(getResources(), blurBg));
                    bmp.recycle();
                }
                final Bitmap finalBlurBg = blurBg;
                alarm_display_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // 对话框取消时释放背景图bitmap
                        if (finalBlurBg != null && !finalBlurBg.isRecycled()) {
                            finalBlurBg.recycle();
                        }
                    }
                });
                alarm_display_dialog.setCancelable(true);
                alarm_display_dialog.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmReceiver);
    }

}
