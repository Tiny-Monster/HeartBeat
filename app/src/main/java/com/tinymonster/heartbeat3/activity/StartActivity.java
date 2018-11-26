package com.tinymonster.heartbeat3.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiUser;
import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.entity.BBSArticle;
import com.tinymonster.heartbeat3.entity.BBSComment;
import com.tinymonster.heartbeat3.entity.GreenDaoManager;
import com.tinymonster.heartbeat3.entity.Rate;
import com.tinymonster.heartbeat3.entity.RateDao;
import com.tinymonster.heartbeat3.entity.entity_alarm;
import com.tinymonster.heartbeat3.entity.entity_alarmDao;
import com.tinymonster.heartbeat3.entity.entity_device;
import com.tinymonster.heartbeat3.entity.entity_deviceDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
    检查数据库中是否有缓存的登陆信息，如果有就直接进入主界面，否则进入登陆界面
 */
public class StartActivity extends AppCompatActivity {
    private static final int GO_HOME=8;
    private static final int GO_LOGIN=9;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private boolean state;
    public static Context StartContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        StartContext=StartActivity.this;
        initJPush();
        Core.initialize(this);
        DroiObject.registerCustomClass(BBSArticle.class);
        DroiObject.registerCustomClass(DroiUser.class);
        DroiObject.registerCustomClass(BBSComment.class);
        requestPermission();
    }
    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(StartActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(StartActivity.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(StartActivity.this,Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED
                ){
            ActivityCompat.requestPermissions(StartActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA},1);
        }else {
            init();
        }
    }
    private void init(){
        preferences= PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
        state=preferences.getBoolean("state",false);
        if (state)//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        {
            mHandler.sendEmptyMessageDelayed(GO_HOME, 500);
        } else {
            mHandler.sendEmptyMessageAtTime(GO_LOGIN, 500);
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME://去主页
                    Log.e("Handle去主页"," ");
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GO_LOGIN://去登录页
                    Log.e("Handle去登陆页"," ");
                    Intent intent2 = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        }
    };
    private void initJPush(){
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
    /*
    数据库操作
     */
    public static entity_deviceDao getDeviceDao(){
        return GreenDaoManager.getInstance().getSession().getEntity_deviceDao();
    }
    public static void insertDevice(String device_name,String device_password,String nick_name,String sex){
        entity_device entity_device=new entity_device(null,device_name,device_password,nick_name,sex,null,"暂无数据");
        getDeviceDao().insert(entity_device);
    }
    public static void deleteDevice(String device_name){
        Query<entity_device> query=getDeviceDao().queryBuilder().where(entity_deviceDao.Properties.Device_name.eq(device_name))
                .build();
        List<entity_device> list=query.list();
        for(entity_device device:list){
            getDeviceDao().delete(device);
        }
    }
    public static List<entity_device> getAllDevice(){
        Query<entity_device> query=getDeviceDao().queryBuilder().build();
        List<entity_device> list=query.list();
        if(list==null){
            return null;
        }else {
            return list;
        }
    }
    public static entity_device getDeviceByName(String device_name){
        Query<entity_device> query=getDeviceDao().queryBuilder().where(entity_deviceDao.Properties.Device_name.eq(device_name))
                .build();
        List<entity_device> list=query.list();
        if(list.size()==0){
            Log.e("DaoError","未查询到数据");
            return null;
        }else {
            return list.get(list.size()-1);
        }
    }
    public static void deleteDeviceById(Long id){
        Query<entity_device> query=getDeviceDao().queryBuilder().where(entity_deviceDao.Properties.Id.eq(id))
                .build();
        List<entity_device> list=query.list();
        for(entity_device device:list){
            getDeviceDao().delete(device);
        }
    }
    public static void UpdateDeviceRateByAccount(String device_name,String rate){
        Query<entity_device> query=getDeviceDao().queryBuilder().where(entity_deviceDao.Properties.Device_name.eq(device_name))
                .build();
        List<entity_device> list=query.list();
        for(entity_device device:list){
            device.setHeart_rate_now(rate);
            getDeviceDao().update(device);
        }
    }
    public static RateDao getRateDao(){
        return GreenDaoManager.getInstance().getSession().getRateDao();
    }
    public static boolean insertRate(int beatNum,String beatTime,String device_name){
        Rate rate=new Rate(null,beatNum,beatTime,device_name);
        List<Rate> list=getAllRateByDeviceName(device_name);
        if(list!=null){
            Rate rate1=list.get(list.size()-1);
            if(rate1.getBeatTime().equals(beatTime)){
                return false;
            }else {
                getRateDao().insert(rate);
                return true;
            }
        }else {
            getRateDao().insert(rate);
            return true;
        }
    }
    public static List<Rate> getAllRateByDeviceName(String device_name){
        Query<Rate> query=getRateDao().queryBuilder().where(RateDao.Properties.Device_name.eq(device_name)).build();
        List<Rate> query_list=query.list();
        List<Rate> list=new ArrayList<>();
        if(query_list.size()>50){
            list=query_list.subList(query_list.size()-50,query_list.size());
            return list;
        }else if(query_list.size()!=0){
            list=query_list;
            return list;
        }else {
            return null;
        }
    }
    public static entity_alarmDao getAlarmDao(){
        return GreenDaoManager.getInstance().getSession().getEntity_alarmDao();
    }
    public static void insertAlarm(String name,String msg,int hour,int minute,String alarmID){
        entity_alarm temp_alarm=new entity_alarm(null,name,msg,hour,minute,alarmID);
        getAlarmDao().insert(temp_alarm);
    }
    public static void deleteAlarmByAlarmId(String alarmId){
        entity_alarm temp_alarm=getAlarmDao().queryBuilder().where(entity_alarmDao.Properties.AlarmID.eq(alarmId))
                .build().unique();
        getAlarmDao().delete(temp_alarm);
    }
    public static entity_alarm getAlarmByAlarmId(String alarmID){
        entity_alarm temp_alarm=getAlarmDao().queryBuilder().where(entity_alarmDao.Properties.AlarmID.eq(alarmID))
                .build().unique();
        return temp_alarm;
    }
    public static List<entity_alarm> getAllAlarm(){
        Query<entity_alarm> query=getAlarmDao().queryBuilder().build();
        List<entity_alarm> list=query.list();
        if(list==null){
            return null;
        }else {
            return list;
        }
    }
    public static entity_alarm getLastAlarm(){
        Query<entity_alarm> alarmQuery=getAlarmDao().queryBuilder().orderAsc(entity_alarmDao.Properties.Id).build();
        List<entity_alarm> list=alarmQuery.list();
        return list.get(list.size()-1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    init();
                }else {
                    Toast.makeText(StartActivity.this,"您需要授权所需的权限",Toast.LENGTH_SHORT).show();
                    requestPermission();
                }
        }
    }
}
