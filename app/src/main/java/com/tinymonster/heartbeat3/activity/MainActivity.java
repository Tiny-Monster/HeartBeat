package com.tinymonster.heartbeat3.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.google.gson.Gson;
import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.adapter.main_item_adapter;
import com.tinymonster.heartbeat3.config.ConfigEntity;
import com.tinymonster.heartbeat3.config.ConfigService;
import com.tinymonster.heartbeat3.entity.JsonRate;
import com.tinymonster.heartbeat3.entity.entity_alarm;
import com.tinymonster.heartbeat3.entity.entity_device;
import com.tinymonster.heartbeat3.util.BitmapBlurUtil;
import com.tinymonster.heartbeat3.util.HttpUtil;
import com.tinymonster.heartbeat3.util.RoleInfo;
import com.tinymonster.heartbeat3.util.VideoConfig;
import com.tinymonster.heartbeat3.view.StepArcView;
import com.volokh.danylo.layoutmanager.LondonEyeLayoutManager;
import com.volokh.danylo.layoutmanager.scroller.IScrollHandler;
import com.volokh.danylo.utils.DebugRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.tinymonster.heartbeat3.util.HttpUtil.JSON;

public class MainActivity extends AppCompatActivity implements AnyChatBaseEvent {
    public static final String REFRESHRATE="com.tinymonster.heartbeat3.REFRESHRATE";
    private DrawerLayout main_drawerlayout;
    private ActionBar main_actionBar;
    private Toolbar toolbar;
    private ImageView Main_add;
    private DebugRecyclerView mRecyclerView;
    private List<entity_device> list;
    private LondonEyeLayoutManager mLondonEyeLayoutManager;
    private main_item_adapter item_adapter;
    private String TAG="MainActivity";
    private Dialog dialog;
    public static Context main_context;
    private NavigationView main_nav_view;
    private View dialog_view;
    private Button main_logout;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    /*
    dialog控件
     */
    private EditText main_dialog_account;
    private EditText main_dialog_pwd;
    private EditText main_dialog_name;
    private EditText main_dialog_sex;
    private ImageView main_dialog_ok;
    private ImageView main_dialog_delete;
    int screenHeight;
    int screenWidth;
    int circleRadius;
    int xOrigin = -200;
    int yOrigin = 0;
    private TextView main_drawerlayout_account;
    private String account_name;
    private LinearLayout drawerlayout;
    /*
    mian_info控件
     */
    private CardView main_info_cardview_empty;
    private CardView main_info_cardview_message;
    private TextView main_info_name_TextView;
    private TextView main_info_sex_TextView;
    private StepArcView main_info_StepArcView;
    private ImageView main_info_moreInfo_ImageView;
    private ImageView main_info_video;
    /*
    刷新广播
     */
    private RefreshReceiver refreshReceiver;
    private IntentFilter intentFilter;
    /*
    anychat相关
    */
    private String LoginAccount;
    private String mStrIP = "39.105.104.164";
    private String mStrName = "小怪兽";
    private int mSPort = 8906;
    private int mSRoomID = 66;
    private List<RoleInfo> mRoleInfoList = new ArrayList<RoleInfo>();
    public AnyChatCoreSDK anyChatSDK;
    private final int LOCALVIDEOAUTOROTATION = 1; // 本地视频自动旋转控制
    private int UserselfID;
    public static final int ACTIVITY_ID_VIDEOCONFIG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        main_context=getApplicationContext();
        initView();
        refreshReceiver=new MainActivity.RefreshReceiver();
        intentFilter=new IntentFilter();
        intentFilter.addAction(REFRESHRATE);
        registerReceiver(refreshReceiver,intentFilter);
        /*
        视频相关
         */
        InitSDK();
        readLoginDate();
        ApplyVideoConfig();
        registerBoradcastReceiver();
        //anychat登陆
        anyChatSDK.Connect(mStrIP, mSPort);
        /***
         * AnyChat支持多种用户身份验证方式，包括更安全的签名登录，
         * 详情请参考：http://bbs.anychat.cn/forum.php?mod=viewthread&tid=2211&highlight=%C7%A9%C3%FB
         */
        anyChatSDK.Login(mStrName, "");
    }

    /*
    初始化控件
     */
    private void initView(){
        preferences= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        LoginAccount=preferences.getString("name","小怪兽");
        Log.e("MainActivity",LoginAccount);
        mStrName=LoginAccount;
        main_logout=(Button)findViewById(R.id.main_logout);
        main_info_video=(ImageView)findViewById(R.id.main_info_video);
        main_nav_view=(NavigationView)findViewById(R.id.main_nav_view);
        main_info_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(RoleInfo roleInfo:mRoleInfoList){
                    Log.e("MainActivity",roleInfo.getName());
                    Log.e("MainActivity","当前用户："+main_info_name_TextView.getText().toString());

                    if(roleInfo.getName().equals(main_info_name_TextView.getText().toString())){
                        Intent intent=new Intent();
                        intent.putExtra("UserID", roleInfo.getUserID());
                        intent.setClass(MainActivity.this,VideoActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
                Toast.makeText(MainActivity.this,"该用户未上线",Toast.LENGTH_SHORT).show();
            }
        });

        main_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                editor=preferences.edit();
                editor.putBoolean("state",false);
                editor.putString("name"," ");
                editor.apply();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        main_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.main_nav_hreat:
                        //心率
                        break;
                    case R.id.main_nav_medicine:
                        Intent intent_plan=new Intent(MainActivity.this,AlarmActivity.class);
                        startActivity(intent_plan);
                        //服药计划
                        break;
                    case R.id.main_nav_doctor:
                        Intent intent_doctor=new Intent(MainActivity.this,DectorActivity.class);
                        startActivity(intent_doctor);
                        //智能医生
                        break;
                    case R.id.main_nav_contact:
                        Intent intent=new Intent(MainActivity.this,ContactActivity.class);
                        startActivity(intent);
                        //联系我们
                        break;
                    case R.id.main_nav_BSS:
                        Intent intent_BBS=new Intent(MainActivity.this,BBSActivity.class);
                        startActivity(intent_BBS);
                        break;
                    case R.id.main_nav_news:
                        Intent intent_new=new Intent(MainActivity.this,WebNewsActivity.class);
                        startActivity(intent_new);
                        break;
                }
                return false;
            }
        });
        final LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
        /*
        recycleView配置
         */
        screenHeight= getApplication().getResources().getDisplayMetrics().heightPixels;
        screenWidth=getApplication().getResources().getDisplayMetrics().widthPixels;
        circleRadius=screenWidth;
        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setParameters(circleRadius,xOrigin,yOrigin);
        mRecyclerView.setHasFixedSize(true);
        mLondonEyeLayoutManager = new LondonEyeLayoutManager(
                circleRadius,
                xOrigin,
                yOrigin,
                mRecyclerView,
                IScrollHandler.Strategy.NATURAL);
        mRecyclerView.setLayoutManager(mLondonEyeLayoutManager);
        list=StartActivity.getAllDevice();
        item_adapter=new main_item_adapter(list);

        mRecyclerView.setAdapter(item_adapter);

        /*
        主页信息控件
         */
        main_info_cardview_empty=(CardView)findViewById(R.id.main_info_cardview_empty);
        main_info_cardview_message=(CardView)findViewById(R.id.main_info_cardview_message);
        main_info_name_TextView=(TextView)findViewById(R.id.main_info_name_TextView);
        main_info_sex_TextView=(TextView)findViewById(R.id.main_info_sex_TextView);
        main_info_StepArcView=(StepArcView)findViewById(R.id.main_info_StepArcView);
        main_info_moreInfo_ImageView=(ImageView)findViewById(R.id.main_info_moreInfo_ImageView);
        main_info_video=(ImageView)findViewById(R.id.main_info_video);
        if(list.size()>0){
            main_info_cardview_empty.setVisibility(View.INVISIBLE);
            main_info_cardview_message.setVisibility(View.VISIBLE);
        }
        /*
        控件配置
         */
        Main_add=(ImageView)findViewById(R.id.Main_add);
        toolbar=(Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        main_drawerlayout=(DrawerLayout)findViewById(R.id.main_drawerlayout);
        main_actionBar=getSupportActionBar();
        if(main_actionBar!=null){
            main_actionBar.setDisplayHomeAsUpEnabled(true);
            main_actionBar.setHomeAsUpIndicator(R.drawable.main_menu);
        }
        /*
        控件点击事件
         */
        Main_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MainActivity.this,R.style.SquareEntranceDialogStyle);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View layout = View.inflate(MainActivity.this, R.layout.main_dialog, null);
                dialog.setContentView(layout);
                Window window = dialog.getWindow();
                        /*
                        dialog控件初始化
                        */
                dialog_view=inflater.inflate(R.layout.main_dialog,null);
                main_dialog_account=(EditText)dialog.getWindow().findViewById(R.id.main_dialog_account);
                main_dialog_pwd=(EditText)dialog.getWindow().findViewById(R.id.main_dialog_pwd);
                main_dialog_name=(EditText)dialog.getWindow().findViewById(R.id.main_dialog_name);
                main_dialog_sex=(EditText)dialog.getWindow().findViewById(R.id.main_dialog_sex);
                main_dialog_ok=(ImageView)dialog.getWindow().findViewById(R.id.main_dialog_ok);
                main_dialog_delete=(ImageView)dialog.getWindow().findViewById(R.id.main_dialog_delete);
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
                    blurBg = BitmapBlurUtil.blur(MainActivity.this, bmp);
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
                        /*
                        dialog点击事件
                         */
                main_dialog_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String account=main_dialog_account.getText().toString();
                        final String pwd=main_dialog_pwd.getText().toString();
                        final String name=main_dialog_name.getText().toString();
                        final String sex=main_dialog_sex.getText().toString();
                        if(account.equals("")||account==null||pwd.equals("")||pwd==null||name.equals("")||name==null||sex.equals("")||sex==null){
                            Toast.makeText(MainActivity.this,"输入信息不能为空",Toast.LENGTH_SHORT).show();
                        }else {
                            JSONObject jsonObject=new JSONObject();
                            try {
                                jsonObject.put("userName",account);
                                jsonObject.put("userPassword",pwd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
                            HttpUtil.sendOkHttpPost("http://39.105.104.164/heartbeat/login", requestBody, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if(response!=null){
                                        String result=response.body().string();
                                        if(result.equals("login success")){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                                                    StartActivity.insertDevice(account,pwd,name,sex);
                                                    list.add(StartActivity.getDeviceByName(account));
                                                    item_adapter.notifyDataSetChanged();
                                                    main_info_cardview_empty.setVisibility(View.INVISIBLE);
                                                    main_info_cardview_message.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }else if(result.equals("password is wrong")){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getApplicationContext(),"网络错误",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(),"网络错误",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });
                main_dialog_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
    /*
    ActionBar 点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                main_drawerlayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
    class RefreshReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("MainActivity","收到广播");
            if(intent.getAction().equals(REFRESHRATE)){
                String device_name=intent.getStringExtra("DEVICENAME");
                String nick_name=intent.getStringExtra("NICKNAME");
                refresh(device_name,nick_name);
            }
        }
    }
    /*
刷新
 */
    public void refresh(final String device_name,final String nick_name){
        final String name=device_name;
        final  String NickName=nick_name;
        Log.e("HeartRateActivity","刷新"+name);
        String url="http://39.105.104.164/heartbeat/getbeat/"+device_name;
        HttpUtil.sendOkHttpGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                Log.e("HeartRateActivity",json);
                try {
                    Gson gson = new Gson();
                    final JsonRate ratetemp = gson.fromJson(json, JsonRate.class);
                    StartActivity.UpdateDeviceRateByAccount(name,String.valueOf(ratetemp.getRate()));
                    boolean state=StartActivity.insertRate(ratetemp.getRate(), ratetemp.getBeatTime(),name);
                    if(!state){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,NickName+"：暂无新信息",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,NickName+"：数据已更新",Toast.LENGTH_SHORT).show();
                                main_info_StepArcView.setCurrentCount(180,ratetemp.getRate());
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    /*
    视频相关
     */
    private void InitSDK() {
        if (anyChatSDK == null) {
            anyChatSDK = AnyChatCoreSDK.getInstance(this);
            anyChatSDK.SetBaseEvent(this);
            anyChatSDK.SetSDKOptionInt(AnyChatDefine.
                    BRAC_SO_CORESDK_USEARMV6LIB, 1);
            anyChatSDK.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION,
                    LOCALVIDEOAUTOROTATION);
        }
    }
    // 读取登陆数据
    private void readLoginDate() {
        SharedPreferences preferences = getSharedPreferences("LoginInfo", 0);
        mStrIP = preferences.getString("UserIP", "39.105.104.164");
        mStrName = preferences.getString("UserName", "小怪兽");
        mSPort = preferences.getInt("UserPort", 8906);
        mSRoomID = preferences.getInt("UserRoomID", 66);
    }
    // 保存登陆相关数据
    private void saveLoginData() {
        SharedPreferences preferences = getSharedPreferences("LoginInfo", 0);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("UserIP", mStrIP);
        preferencesEditor.putString("UserName", mStrName);
        preferencesEditor.putInt("UserPort", mSPort);
        preferencesEditor.putInt("UserRoomID", mSRoomID);
        preferencesEditor.apply();
        preferencesEditor.commit();
    }
    // 根据配置文件配置视频参数
    private void ApplyVideoConfig() {
        ConfigEntity configEntity = ConfigService.LoadConfig(this);
        if (configEntity.mConfigMode == 1) // 自定义视频参数配置
        {
            // 设置本地视频编码的码率（如果码率为0，则表示使用质量优先模式）
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_BITRATECTRL,
                    configEntity.mVideoBitrate);
//			if (configEntity.mVideoBitrate == 0) {
            // 设置本地视频编码的质量
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_QUALITYCTRL,
                    configEntity.mVideoQuality);
//			}
            // 设置本地视频编码的帧率
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_FPSCTRL,
                    configEntity.mVideoFps);
            // 设置本地视频编码的关键帧间隔
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_GOPCTRL,
                    configEntity.mVideoFps * 4);
            // 设置本地视频采集分辨率
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL,
                    configEntity.mResolutionWidth);
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL,
                    configEntity.mResolutionHeight);
            // 设置视频编码预设参数（值越大，编码质量越高，占用CPU资源也会越高）
            AnyChatCoreSDK.SetSDKOptionInt(
                    AnyChatDefine.BRAC_SO_LOCALVIDEO_PRESETCTRL,
                    configEntity.mVideoPreset);
        }
        // 让视频参数生效
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_APPLYPARAM,
                configEntity.mConfigMode);
        // P2P设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_NETWORK_P2PPOLITIC,
                configEntity.mEnableP2P);
        // 本地视频Overlay模式设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_OVERLAY,
                configEntity.mVideoOverlay);
        // 回音消除设置
        AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_ECHOCTRL,
                configEntity.mEnableAEC);
        // 平台硬件编码设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_CORESDK_USEHWCODEC,
                configEntity.mUseHWCodec);
        // 视频旋转模式设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_ROTATECTRL,
                configEntity.mVideoRotateMode);
        // 本地视频采集偏色修正设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_FIXCOLORDEVIA,
                configEntity.mFixColorDeviation);
        // 视频GPU渲染设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_VIDEOSHOW_GPUDIRECTRENDER,
                configEntity.mVideoShowGPURender);
        // 本地视频自动旋转设置
        AnyChatCoreSDK.SetSDKOptionInt(
                AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION,
                configEntity.mVideoAutoRotation);
    }
    /*
    广播
     */
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("VideoActivity");
        // 注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
    // 广播
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("VideoActivity")) {
                Toast.makeText(MainActivity.this, "网络已断开！", Toast.LENGTH_SHORT)
                        .show();
                //setBtnVisible(SHOWLOGINSTATEFLAG);
                //mRoleList.setAdapter(null);
                //mBottomConnMsg.setText("No content to the server");
                anyChatSDK.LeaveRoom(-1);
                anyChatSDK.Logout();
            }
        }
    };

    @Override
    protected void onDestroy() {
        anyChatSDK.LeaveRoom(-1);
        anyChatSDK.Logout();
        anyChatSDK.removeEvent(this);
        anyChatSDK.Release();
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        anyChatSDK.SetBaseEvent(this);

    }
    @Override
    public void OnAnyChatConnectMessage(boolean bSuccess) {
        //
        if (!bSuccess) {
            Toast.makeText(MainActivity.this,"连接服务器失败，自动重连，请稍后...",Toast.LENGTH_SHORT).show();
            System.out.println("connect failed");
        }
    }
    @Override
    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
        if (dwErrorCode == 0) {
            saveLoginData();
            Toast.makeText(MainActivity.this,"视频服务器连接成功",Toast.LENGTH_SHORT).show();
            int sHourseID = mSRoomID;
            anyChatSDK.EnterRoom(sHourseID, "");
            UserselfID = dwUserId;
            // finish();
        } else {
            Toast.makeText(MainActivity.this,"视频服务器连接失败，errorCode:"+dwErrorCode,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
        System.out.println("OnAnyChatEnterRoomMessage" + dwRoomId + "err:"
                + dwErrorCode);
    }
    @Override
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
        Toast.makeText(MainActivity.this,"进入房间成功",Toast.LENGTH_SHORT).show();
        updateUserList();
    }
    private void updateUserList() {
        mRoleInfoList.clear();
        int[] userID = anyChatSDK.GetOnlineUser();//获取在线的人数的ID
        RoleInfo userselfInfo = new RoleInfo();
        userselfInfo.setName(anyChatSDK.GetUserName(UserselfID)
                + "(自己) 【点击可设置】");
        userselfInfo.setUserID(String.valueOf(UserselfID));
        userselfInfo.setRoleIconID(getRoleRandomIconID());
        mRoleInfoList.add(userselfInfo);

        for (int index = 0; index < userID.length; ++index) {
            RoleInfo info = new RoleInfo();
            info.setName(anyChatSDK.GetUserName(userID[index]));
            info.setUserID(String.valueOf(userID[index]));
            info.setRoleIconID(getRoleRandomIconID());
            mRoleInfoList.add(info);
        }
        /*
        不使用LIST直接进入
         */
//
//        mAdapter = new RoleListAdapter(MainActivity.this, mRoleInfoList);
//        mRoleList.setAdapter(mAdapter);
//        mRoleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                if (arg2 == 0) {
//                    Intent intent = new Intent();
//                    intent.setClass(MainActivity.this, VideoConfig.class);
//                    startActivityForResult(intent, ACTIVITY_ID_VIDEOCONFIG);
//                    return;
//                }
//
//                onSelectItem(arg2);
//            }
//        });
    }
    //进入房间
    private void onSelectItem(int postion) {
        String strUserID = mRoleInfoList.get(postion).getUserID();
        Intent intent = new Intent();
        intent.putExtra("UserID", strUserID);
        intent.setClass(this, VideoActivity.class);
        startActivity(intent);
    }

    private int getRoleRandomIconID() {
        int number = new Random().nextInt(5) + 1;
        if (number == 1) {
            return R.drawable.role_1;
        } else if (number == 2) {
            return R.drawable.role_2;
        } else if (number == 3) {
            return R.drawable.role_3;
        } else if (number == 4) {
            return R.drawable.role_4;
        } else if (number == 5) {
            return R.drawable.role_5;
        }
        return R.drawable.role_1;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK && requestCode == ACTIVITY_ID_VIDEOCONFIG) {
            ApplyVideoConfig();
        }
    }
    //更新房间用户信息
    @Override
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
        if (bEnter) {
            RoleInfo info = new RoleInfo();
            info.setUserID(String.valueOf(dwUserId));
            info.setName(anyChatSDK.GetUserName(dwUserId));
            info.setRoleIconID(getRoleRandomIconID());
            mRoleInfoList.add(info);
        } else {

            for (int i = 0; i < mRoleInfoList.size(); i++) {
                if (mRoleInfoList.get(i).getUserID().equals("" + dwUserId)) {
                    mRoleInfoList.remove(i);
                }
            }
        }
    }

    @Override
    public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
        anyChatSDK.LeaveRoom(-1);
        anyChatSDK.Logout();
        Toast.makeText(MainActivity.this,"关闭连接，errorCode:"+dwErrorCode,Toast.LENGTH_SHORT).show();
    }

}
