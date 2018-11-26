package com.tinymonster.heartbeat3.util;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.tinymonster.heartbeat3.R;
import com.tinymonster.heartbeat3.config.ConfigEntity;
import com.tinymonster.heartbeat3.config.ConfigService;


public class VideoConfig extends Activity{
	private ImageButton mImgBtnReturn;
	private TextView mTitleName;
	
	private Button mSaveBtn;
	private ConfigEntity mConfigEntity;
	// 服务器配置
	private RadioButton mServerModelConfigBtn;
	// 自定义配置
	private RadioButton mCustomModelConfigBtn;
	
	private TextView mResolutionTV;
	private CheckBox mEnableP2PBox;
	private CheckBox mVideoOverlayBox;
	private CheckBox mUseARMv6Box;
	private CheckBox mUseAECBox;
	private CheckBox mUseHWCodecBox;
	private CheckBox mVideoRotateBox;
	private CheckBox mFixColorDeviation;
	private CheckBox mVideoShowGPURender;
	private CheckBox mVideoAutoRotation;
	private Spinner mVideoSizeSpinner;
	private Spinner mVideoBitrateSpinner;
	private Spinner mVideoFPSSpinner;
	private Spinner mVideoQualitySpinner;
	private Spinner mVideoPresetSpinner;

	private final String[] mArrVideoSizeStr = {"176 x 144", "320 x 240（默认）", "352 x 288", "640 x 480", "720 x 480", "1280 x 720"};
	private final int[] mArrVideoWidthValue = {176,320,352,640, 720, 1280};
	private final int[] mArrVideoHeightValue = {144,240,288,480, 480, 720};
	
	private final String[] mArrVideoBitrateStr = {"质量优先模式", "60kbps（默认）", "80kbps", "100kbps", "150kbps", "200kbps", "300kbps", "500kbps", "800kbps", "1Mbps"};
	private final int[]	mArrVideoBitrateValue = {0,60*1000,80*1000,100*1000,150*1000,200*1000,300*1000,500*1000,800*1000,1000*1000};
	
	private final String[] mArrVideofpsStr = {"2 FPS", "4 FPS", "6 FPS", "8 FPS", "10FPS（默认）", "15FPS", "20FPS", "25FPS"};
	private final int[]	mArrVideofpsValue = {2,4,6,8,10,15,20,25};
	
	private final String[] mArrVideoQualityStr = {"普通视频质量", "中等视频质量（默认）", "较好视频质量"};
	private final int[] mArrVideoQualityValue = {2,3,4};
	
	private final String[] mArrVideoPresetStr = {"最高效率，较低质量","较高效率，较低质量","性能均衡（默认）","较高质量，较低效率","最高质量，较低效率"};
	private final int[] mArrVideoPresetValue = {1,2,3,4,5};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		mConfigEntity = ConfigService.LoadConfig(this);
		setContentView(R.layout.videoconfig);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		mImgBtnReturn = (ImageButton) this.findViewById(R.id.returnImgBtn);
		mTitleName = (TextView) this.findViewById(R.id.titleName);
		mImgBtnReturn.setOnClickListener(onClickListener);
		mTitleName.setText("视频参数设置");
		//初始化界面
		InitialLayout();
	}
	
	private void InitialLayout() {
		
		// 启用P2P网络连接
		mEnableP2PBox = (CheckBox)this.findViewById(R.id.enableP2PBox);
		mEnableP2PBox.setTextColor(Color.BLACK);
		mEnableP2PBox.setChecked(mConfigEntity.mEnableP2P != 0);
		
		// Overlay视频模式
		mVideoOverlayBox = (CheckBox)this.findViewById(R.id.videoOverlayBox);
		mVideoOverlayBox.setTextColor(Color.BLACK);
		mVideoOverlayBox.setChecked(mConfigEntity.mVideoOverlay != 0);
		
		// 翻转视频
		mVideoRotateBox = (CheckBox)this.findViewById(R.id.videoRotateBox);
		mVideoRotateBox.setTextColor(Color.BLACK);
		mVideoRotateBox.setChecked(mConfigEntity.mVideoRotateMode != 0); 
		
		// 本地视频采集偏色修正
		mFixColorDeviation = (CheckBox)this.findViewById(R.id.fixColorDeviation);
		mFixColorDeviation.setTextColor(Color.BLACK);
	    mFixColorDeviation.setChecked(mConfigEntity.mFixColorDeviation != 0);  
		
	    // 启用视频GPU渲染
	    mVideoShowGPURender = (CheckBox)this.findViewById(R.id.videoShowGPURender);
	    mVideoShowGPURender.setTextColor(Color.BLACK);
	    mVideoShowGPURender.setChecked(mConfigEntity.mVideoShowGPURender!=0); 	
	    
	    // 本地视频跟随设备自动旋转
	    mVideoAutoRotation = (CheckBox)this.findViewById(R.id.videoAutoRotation);
	    mVideoAutoRotation.setTextColor(Color.BLACK);
	    mVideoAutoRotation.setChecked(mConfigEntity.mVideoAutoRotation != 0); 
		
	    // 强制使用ARMv6指令集（安全模式）
	    mUseARMv6Box = (CheckBox)this.findViewById(R.id.useARMv6Box);
	    mUseARMv6Box.setTextColor(Color.BLACK);
	    mUseARMv6Box.setChecked(mConfigEntity.mUseARMv6Lib != 0); 
	    
	    // 启用回音消除（AEC）
	    mUseAECBox = (CheckBox)this.findViewById(R.id.useAECBox);
	    mUseAECBox.setTextColor(Color.BLACK);
	    mUseAECBox.setChecked(mConfigEntity.mEnableAEC != 0); 
	    
	    // 启用平台内置硬件编解码（需重启应用程序）
	    mUseHWCodecBox = (CheckBox)this.findViewById(R.id.useHWCodecBox);
	    mUseHWCodecBox.setTextColor(Color.BLACK);
	    mUseHWCodecBox.setChecked(mConfigEntity.mUseHWCodec != 0);
	  
	    // 插入配置模式选择项
	    TextView configModelLable = (TextView)this.findViewById(R.id.configModelLable);
	    configModelLable.setTextColor(Color.BLACK);
	    configModelLable.setText("选择配置模式： ");
	     
	    mServerModelConfigBtn = (RadioButton)findViewById(R.id.serverModelConfigBtn);
	    mCustomModelConfigBtn = (RadioButton)findViewById(R.id.customModelConfigBtn);
	    mServerModelConfigBtn.setTextColor(Color.BLACK);
	    mCustomModelConfigBtn.setTextColor(Color.BLACK);
    	mServerModelConfigBtn.setOnClickListener(onClickListener);
    	mCustomModelConfigBtn.setOnClickListener(onClickListener);
	    
    	if (mConfigEntity.mConfigMode == ConfigEntity.VIDEO_MODE_SERVERCONFIG)
    		mServerModelConfigBtn.setChecked(true);
    	else
    		mCustomModelConfigBtn.setChecked(true);
    		
    	//选择视频分辨率：
    	mResolutionTV = (TextView)this.findViewById(R.id.resolutionTV);
    	mResolutionTV.setTextColor(Color.BLACK);
   	
    	// 插入视频分辨率
    	mVideoSizeSpinner = (Spinner)this.findViewById(R.id.videoSizeSpinner);
    	ArrayAdapter<String> videoSizeAdapter; 
    	videoSizeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mArrVideoSizeStr);  
    	videoSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
    	mVideoSizeSpinner.setAdapter(videoSizeAdapter);  
    	mVideoSizeSpinner.setVisibility(View.VISIBLE);
    	int iSelectVideoSize = 0;
    	for(int i=0; i<mArrVideoWidthValue.length; i++)
    	{
    		if(mArrVideoWidthValue[i] == mConfigEntity.mResolutionWidth)
    		{
    			iSelectVideoSize = i;
    			break;
    		}
    	}
    	mVideoSizeSpinner.setSelection(iSelectVideoSize);

    	//插入码率
    	mVideoBitrateSpinner = InsertSpinnerInterface(1, mArrVideoBitrateStr, mArrVideoBitrateValue, mConfigEntity.mVideoBitrate);
    	//插入帧率
    	mVideoFPSSpinner = InsertSpinnerInterface(2, mArrVideofpsStr, mArrVideofpsValue, mConfigEntity.mVideoFps); 	
    	//插入视频质量
    	mVideoQualitySpinner = InsertSpinnerInterface(3, mArrVideoQualityStr, mArrVideoQualityValue, mConfigEntity.mVideoQuality);
    	// 插入视频预设参数
    	mVideoPresetSpinner = InsertSpinnerInterface(4, mArrVideoPresetStr, mArrVideoPresetValue, mConfigEntity.mVideoPreset);
    	
    	// 根据配置模式，确定是否需要显示自定义的配置项
    	CustomControlsShow(mConfigEntity.mConfigMode == 0 ? false : true);
    	
    	mSaveBtn = (Button)this.findViewById(R.id.saveBtn);
    	mSaveBtn.setText("保存设置");
    	mSaveBtn.setOnClickListener(onClickListener);
	}
		
	OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == mSaveBtn){
				SaveConfig();
			}else if (v == mCustomModelConfigBtn) {
				CustomControlsShow(true);
			}else if (v == mServerModelConfigBtn) {
				CustomControlsShow(false);
			}else if (v == mImgBtnReturn) {
				destroyCurActivity();
			}
		}
	};
	
	private void CustomControlsShow(boolean bEnable) {
		mVideoSizeSpinner.setEnabled(bEnable);
    	mVideoBitrateSpinner.setEnabled(bEnable);
    	mVideoFPSSpinner.setEnabled(bEnable);
    	mVideoQualitySpinner.setEnabled(bEnable);
    	mVideoPresetSpinner.setEnabled(bEnable);	    	
	}
	
	private void SaveConfig()
	{
		mConfigEntity.mConfigMode = mServerModelConfigBtn.isChecked() ? ConfigEntity.VIDEO_MODE_SERVERCONFIG : ConfigEntity.VIDEO_MODE_CUSTOMCONFIG;
		mConfigEntity.mResolutionWidth = mArrVideoWidthValue[mVideoSizeSpinner.getSelectedItemPosition()];
		mConfigEntity.mResolutionHeight = mArrVideoHeightValue[mVideoSizeSpinner.getSelectedItemPosition()];
		mConfigEntity.mVideoBitrate = mArrVideoBitrateValue[mVideoBitrateSpinner.getSelectedItemPosition()];
		mConfigEntity.mVideoFps = mArrVideofpsValue[mVideoFPSSpinner.getSelectedItemPosition()];    	
		mConfigEntity.mVideoQuality = mArrVideoQualityValue[mVideoQualitySpinner.getSelectedItemPosition()];  
		mConfigEntity.mVideoPreset = mArrVideoPresetValue[mVideoPresetSpinner.getSelectedItemPosition()]; 

		mConfigEntity.mVideoOverlay = mVideoOverlayBox.isChecked() ? 1 : 0;
		mConfigEntity.mVideoRotateMode = mVideoRotateBox.isChecked() ? 1 : 0;
		mConfigEntity.mFixColorDeviation = mFixColorDeviation.isChecked() ? 1 : 0;
		mConfigEntity.mVideoShowGPURender = mVideoShowGPURender.isChecked() ? 1 : 0;
		
		mConfigEntity.mVideoAutoRotation = mVideoAutoRotation.isChecked() ? 1 : 0;
		
		mConfigEntity.mEnableP2P = mEnableP2PBox.isChecked() ? 1 : 0;
		mConfigEntity.mUseARMv6Lib = mUseARMv6Box.isChecked() ? 1 : 0;
		mConfigEntity.mEnableAEC = mUseAECBox.isChecked() ? 1 : 0;
		mConfigEntity.mUseHWCodec = mUseHWCodecBox.isChecked() ? 1 : 0;
		
    	ConfigService.SaveConfig(this, mConfigEntity);
    	
    	this.setResult(RESULT_OK);
    	this.finish();
	}
	
	private Spinner InsertSpinnerInterface(int spinnerIndex, String[] context, int[] value, int select) {			
		Spinner spinner = null;
		if (spinnerIndex == 1){
			spinner = (Spinner)this.findViewById(R.id.videoBitrateSpinner);
		}else if (spinnerIndex == 2) {
			spinner = (Spinner)this.findViewById(R.id.videoFPSSpinner);
		}else if (spinnerIndex == 3) {
			spinner = (Spinner)this.findViewById(R.id.videoQualitySpinner);
		}else if (spinnerIndex == 4) {
			spinner = (Spinner)this.findViewById(R.id.videoPresetSpinner);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, context);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setVisibility(View.VISIBLE);
		
		int offset = 0;
		for (int i = 0; i < value.length; i++)
		{
			if (value[i] == select)
			{
				offset = i;
				break;
			}
		}
		
		spinner.setSelection(offset);
		
		return spinner;
	}
	
	private void destroyCurActivity() {
		onPause();
		onDestroy();
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
