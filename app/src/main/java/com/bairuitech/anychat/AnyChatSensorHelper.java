package com.bairuitech.anychat;


import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;

import java.util.Date;


// AnyChat Camera包装类，实现本地视频采集
public class AnyChatSensorHelper implements SensorEventListener{

	private AnyChatOrientationEventListener orientationListener = null;
	
	public void InitSensor(Context context) {
		// 获取设备类型
		int iDeviceType = ((context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE) ? 2 : 1;
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_DEVICEMODE, iDeviceType);
		// 设置屏幕旋转方向
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int rotation = wm.getDefaultDisplay ().getRotation ();
		int degrees = 0 ;
		switch ( rotation ) {
			case Surface.ROTATION_0 : degrees = 0 ; break ;
			case Surface.ROTATION_90 : degrees = 90 ; break ;
			case Surface.ROTATION_180 : degrees = 180 ; break ;
			case Surface.ROTATION_270 : degrees = 270 ; break ;
		}
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_SURFACEROTATION, degrees);

		AnyChatCoreSDK.mCameraHelper.SetContext(context);
		AnyChatCoreSDK.mAudioHelper.SetContext(context);

		if(orientationListener == null) {
			orientationListener = new AnyChatOrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL);
		}
		orientationListener.enable();
		// 获取传感器管理服务
		SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		// 获取加速度传感器
		Sensor mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// 获取加速度传感器的消息，SensorManager. SENSOR_DELAY_NORMAL表示获取周期，取正常即可
		sm.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void DestroySensor() {
		orientationListener.disable();
	}

	private float LastXSpead = 0;
	private float LastYSpead = 0;
	private float LastZSpead = 0;

	private boolean bCameraNeedFocus = false; // 摄像机是否需要对焦
	private Date LastSportTime = new Date(); // 上次运动时间

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
            return;
        }
		float X = event.values[0]; // 水平x方向加速度 ，物体静止时在（0--1之间）
		float Y = event.values[1]; // 水平Y方向加速度 ，物体静止时在（0--1之间）
		float Z = event.values[2]; // 竖直Z方向加速度 ，物体静止时在（9.5--10之间）

		if ((Math.abs(X - LastXSpead) <= 0.5) && (Math.abs(Y - LastYSpead) <= 0.5) && (Math.abs(Z - LastZSpead) <= 0.5)) // 静止状态
		{
			Date now = new Date();
			long interval = now.getTime() - LastSportTime.getTime();
			if (bCameraNeedFocus && interval > 1000) {
				bCameraNeedFocus = false;
				// 如果是采用Java视频采集，则在Java层进行摄像头自动对焦操作
				if(AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA)
					AnyChatCoreSDK.mCameraHelper.CameraAutoFocus();
				else
					AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FOCUSCTRL, 1);
			}
		} else {
			bCameraNeedFocus = true;
			LastSportTime.setTime(System.currentTimeMillis());
		}
		LastXSpead = X;
		LastYSpead = Y;
		LastZSpead = Z;
	}
}


class AnyChatOrientationEventListener extends OrientationEventListener{
	public AnyChatOrientationEventListener(Context context, int rate) { 
		super(context, rate);  
	}
	   
	@Override public void onOrientationChanged(int degree) {
		int ANYCHAT_DEVICEORIENTATION_UNKNOW 			= 0;
		int ANYCHAT_DEVICEORIENTATION_FACEUP			= 1;		// Device oriented flat, face up
//		int ANYCHAT_DEVICEORIENTATION_FACEDOWN			= 2;		// Device oriented flat, face down
		int ANYCHAT_DEVICEORIENTATION_LANDSCAPELEFT		= 3;		// Device oriented horizontally, home button on the right
		int ANYCHAT_DEVICEORIENTATION_LANDSCAPERIGHT	= 4;		// Device oriented horizontally, home button on the left
		int ANYCHAT_DEVICEORIENTATION_PORTRAIT			= 5;		// Device oriented vertically, home button on the bottom
		int ANYCHAT_DEVICEORIENTATION_PORTRAITUPSIDE	= 6;		// Device oriented vertically, home button on the top
		
		//确定由角度与屏幕方向的对应范围  
		int orientation = ANYCHAT_DEVICEORIENTATION_UNKNOW;
		if(degree == -1)
			orientation = ANYCHAT_DEVICEORIENTATION_FACEUP;
		else if(degree > 325 || degree <= 45){  
        	orientation = ANYCHAT_DEVICEORIENTATION_PORTRAIT;  
        }else if(degree > 45 && degree <= 135){  
        	orientation = ANYCHAT_DEVICEORIENTATION_LANDSCAPERIGHT;
        }else if(degree > 135 && degree < 225){  
        	orientation = ANYCHAT_DEVICEORIENTATION_PORTRAITUPSIDE; 
        }else {  
        	orientation = ANYCHAT_DEVICEORIENTATION_LANDSCAPELEFT;
        }		
        //Log.i("ANYCHAT", "onOrientationChanged: " + degree + "orientation:" +orientation );
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_ORIENTATION, orientation);
	}
}