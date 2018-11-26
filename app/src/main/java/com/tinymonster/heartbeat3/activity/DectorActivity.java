package com.tinymonster.heartbeat3.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.entity.HealthTipEntitySuccess;
import com.tinymonster.heartbeat3.entity.HttpUtils;
import com.tinymonster.heartbeat3.entity.RecognizeEntity;
import com.tinymonster.heartbeat3.util.AutoCheck;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DectorActivity extends AppCompatActivity implements EventListener,SpeechSynthesizerListener{
    private TextView doctor_TextView;
    private ImageView doctor_image;
    /*
    语音合成与识别
    */
    private static String DESC_TEXT = "测试";
    private EventManager asr;
    private boolean logTime = true;
    private boolean enableOffline = false; // 测试离线命令词，需要改成true
    private List<RecognizeEntity> RecognizeResultList=new ArrayList<>();
    private String TAG="DoctorActivity";
    /*
    语音合成
     */
    private SpeechSynthesizer mSpeechSynthesizer;//百度语音合成客户端
    private String mSampleDirPath;

    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";

    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license_2016-04-05";

    private static final String APP_ID = "11470815";//请更换为自己创建的应用
    private static final String API_KEY = "GkGyqX6haRj5uZzoQitXh9GD";//请更换为自己创建的应用
    private static final String SECRET_KEY = "Sz7qvlhd6xC5zGAlq3Z8oHrvDhhgrxzM";//请更换为自己创建的应用
    /*
    健康提示
     */
    final String host = "http://znys.market.alicloudapi.com";
    final String path = "/sent.do";
    final String method = "GET";
    String appcode = "e21c2a298d3d47a3a2398778155bf68b";
    final Map<String, String> headers = new HashMap<String, String>();
    final Map<String, String> querys = new HashMap<String, String>();
    private ImageView doctor_img_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dector);
        initView();
        initPermission();
        initialTts();
        asr = EventManagerFactory.create(this, "asr");
        asr.registerListener(this); //  EventListener 中 onEvent方法
        if (enableOffline) {
            loadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
    }
    private void initView(){
        doctor_img_return=(ImageView)findViewById(R.id.doctor_img_return);
        doctor_img_return=(ImageView)findViewById(R.id.doctor_img_return);
        doctor_TextView=(TextView)findViewById(R.id.doctor_TextView);
        doctor_image=(ImageView)findViewById(R.id.doctor_image);
        doctor_img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        doctor_img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        doctor_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    start();
                    doctor_TextView.setText("点击按钮说话");
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    stop();
                }
                return false;
            }
        });
    }
    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }
    /*
初始化语音客户端并启动
 */
    private void initialTts() {
        //获取语音合成对象实例
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        //设置Context
        this.mSpeechSynthesizer.setContext(this);
        //设置语音合成状态监听
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        //文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        //声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        //本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，
        //仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了离线授权，
        //不需要设置该参数，建议将该行代码删除（离线引擎）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
                + LICENSE_FILE_NAME);
        //请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId(APP_ID);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey(API_KEY, SECRET_KEY);
        //发音人（在线引擎），可用参数为0,1,2,3。。。
        //（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(可以不使用，只是验证授权是否成功)
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.ONLINE);
        if (authInfo.isSuccess()) {
            Log.i(TAG, ">>>auth success.");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.i(TAG, ">>>auth failed errorMsg: " + errorMsg);
        }
        // 引擎初始化tts接口
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);
        // 加载离线英文资源（提供离线英文合成功能）
    }
    private void start() {
        //doctor_TextView.setText("开始");
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event
        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);

        (new AutoCheck(getApplicationContext(), new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
//                        txtLog.append(message + "\n");
                        ; // 可以用下面一行替代，在logcat中查看代码
                        //doctor_TextView.append(message + "\n");
                        Log.w("AutoCheckMessage", message);
                    }
                }
            }
        },enableOffline)).checkAsr(params);
        String json = null; // 可以替换成自己的json
        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);
        //printLog("输入参数：" + json);
    }
    private void stop() {
        //printLog("停止识别：ASR_STOP");
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }
    private void loadOfflineEngine() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
    }

    private void unloadOfflineEngine() {
        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0); //
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        if (enableOffline) {
            unloadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
        this.mSpeechSynthesizer.release();//释放资源
    }

    //   EventListener  回调方法
    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            Log.e("MainActivity","监听到结束");
            Log.e("params:",params);
            Log.e("name:",name);
            Gson gson=new Gson();
            final RecognizeEntity healthTipEntitySuccess=gson.fromJson(params,new TypeToken<RecognizeEntity>(){}.getType());
            RecognizeResultList.add(healthTipEntitySuccess);
        }
        if(name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)){
            if(RecognizeResultList.size()>0){
                headers.put("Authorization", "APPCODE " + appcode);
                querys.put("content", RecognizeResultList.get(RecognizeResultList.size()-1).best_result);
                querys.put("product", "znys");
                querys.put("uuid", "1234567890");
                doctor_TextView.setText("");
                doctor_TextView.append(RecognizeResultList.get(RecognizeResultList.size()-1).best_result+"\n\n\n");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    OkHttpClient client=new OkHttpClient();
                                    Request request=new Request.Builder().addHeader("Authorization","APPCODE e21c2a298d3d47a3a2398778155bf68b")
                                            .url(HttpUtils.buildUrl(host,path,querys)).build();
                                    Response response=client.newCall(request).execute();
                                    String responseDate=response.body().string();
                                    Gson gson=new Gson();
                                    final HealthTipEntitySuccess healthTipEntitySuccess=gson.fromJson(responseDate,new TypeToken<HealthTipEntitySuccess>(){}.getType());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            doctor_TextView.append(healthTipEntitySuccess.msg+"\n");
                                            mSpeechSynthesizer.speak(healthTipEntitySuccess.msg);
                                        }
                                    });
                                    Log.e("OkHttp",responseDate);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        RecognizeResultList.clear();
                    }
                });
            }
        }
    }

    private void printLog(String text) {
        if (logTime) {
            text += "  ;time=" + System.currentTimeMillis();
        }
        text += "\n";
        Log.i(getClass().getName(), text);
        doctor_TextView.append(text + "\n");
    }
    @Override
    public void onSynthesizeStart(String s) {
        //监听到合成开始
        //Log.i(TAG, ">>>onSynthesizeStart()<<< s: " + s);
    }
    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        //监听到有合成数据到达
        //Log.i(TAG, ">>>onSynthesizeDataArrived()<<< s: " + s);
    }
    @Override
    public void onSynthesizeFinish(String s) {
        //监听到合成结束
        // Log.i(TAG, ">>>onSynthesizeFinish()<<< s: " + s);
    }

    @Override
    public void onSpeechStart(String s) {
        //监听到合成并开始播放
        //Log.i(TAG, ">>>onSpeechStart()<<< s: " + s);
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        //监听到播放进度有变化
        //Log.i(TAG, ">>>onSpeechProgressChanged()<<< s: " + s);
    }

    @Override
    public void onSpeechFinish(String s) {
        //监听到播放结束
        //Log.i(TAG, ">>>onSpeechFinish()<<< s: " + s);
    }

    @Override
    public void onError(String s, SpeechError speechError) {
        //监听到出错
        //Log.i(TAG, ">>>onError()<<< description: " + speechError.description + ", code: " + speechError.code);
    }
}
