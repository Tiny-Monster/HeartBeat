package com.bairuitech.anychat;


import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// AnyChat Camera包装类，实现本地视频采集
public class AnyChatCameraHelper implements SurfaceHolder.Callback {
	private final static String TAG = "ANYCHAT";
	private Camera mCamera = null;
	private boolean bIfPreview = false;
	private boolean bNeedCapture = false;
	private int iCurrentCameraId = 0;
	private SurfaceHolder currentHolder = null;
	private int mVideoPixfmt = -1;
	private final int iCaptureBuffers = 3;

	private Context mContext = null;
	private int mCameraOrientation = 0;
	private int mCameraFacing = 0;
	private int mDeviceOrientation = 0;

	public final int CAMERA_FACING_BACK = 0;
	public final int CAMERA_FACING_FRONT = 1;

	// 设置父窗口句柄
	public void SetContext(Context ctx) {
		mContext = ctx;
	}

	// 初始化摄像机，在surfaceCreated中调用
	private void initCamera() {
		if (null == mCamera)
			return;
		try {
			if (bIfPreview) {
				mCamera.stopPreview();// stopCamera();
				mCamera.setPreviewCallbackWithBuffer(null);
			}
			CameraInfo cameraInfo = new CameraInfo();
			Camera.getCameraInfo(iCurrentCameraId, cameraInfo);
			mCameraOrientation = cameraInfo.orientation;
			mCameraFacing = cameraInfo.facing;
			mDeviceOrientation = getDeviceOrientation();
			Log.i(TAG, "allocate: device orientation=" + mDeviceOrientation + ", camera orientation="
					+ mCameraOrientation + ", facing=" + mCameraFacing);

			setCameraDisplayOrientation();

			/* Camera Service settings */
			Camera.Parameters parameters = mCamera.getParameters();

			// 获取camera支持的相关参数，判断是否可以设置
			List<Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
			Collections.sort(previewSizes, new CameraSizeComparator());
			// 获取当前设置的分辩率参数
			int iSettingsWidth = AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL);
			int iSettingsHeight = AnyChatCoreSDK.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL);
			boolean bSetPreviewSize = false;
			boolean bExistDefaultSize = false;
			if (previewSizes.size() == 1) {
				bSetPreviewSize = true;
				parameters.setPreviewSize(previewSizes.get(0).width, previewSizes.get(0).height);
			} else {
				for (int i = 0; i < previewSizes.size(); i++) {
					Size s = previewSizes.get(i);
					if (s.width == iSettingsWidth && s.height == iSettingsHeight) {
						bSetPreviewSize = true;
						parameters.setPreviewSize(iSettingsWidth, iSettingsHeight);
						break;
					}
					if (s.width == 320 && s.height == 240)
						bExistDefaultSize = true;
				}
				if (!bSetPreviewSize) {
					for (int i = 0; i < previewSizes.size(); i++) {
						Size s = previewSizes.get(i);
						if (s.width >= iSettingsWidth || s.height >= iSettingsHeight) {
							bSetPreviewSize = true;
							parameters.setPreviewSize(s.width, s.height);
							break;
						}
					}
				}
			}

			// 指定的分辩率不支持时，如果当前手机支持320x240分辨率，优选设置320x240分辨率否则使用手机支持分辨率中最低的分辨率进行设置
			if (!bSetPreviewSize) {
				if (bExistDefaultSize) {
					parameters.setPreviewSize(320, 240);
				} else if (previewSizes.size() > 0) {
					Size s = previewSizes.get(0);
					parameters.setPreviewSize(s.width, s.height);
				}
			}

			// 设置视频采集帧率
			List<int[]> fpsRange = parameters.getSupportedPreviewFpsRange();
			for (int i = 0; i < fpsRange.size(); i++) {
				int[] r = fpsRange.get(i);
				AnyChatCoreSDK.SetSDKOptionString(AnyChatDefine.BRAC_SO_CORESDK_WRITELOG,
						"Camera FrameRate: " + r[0] + " , " + r[1]);
				if (r[0] >= 25000 && r[1] >= 25000) {
					parameters.setPreviewFpsRange(r[0], r[1]);
					break;
				}
			}

			// 设置视频数据格式
			parameters.setPreviewFormat(ImageFormat.NV21);
			// 参数设置生效
			try {
				mCamera.setParameters(parameters);
			} catch (Exception e) {

			}
			Size captureSize = mCamera.getParameters().getPreviewSize();
			int bufSize = captureSize.width * captureSize.height * ImageFormat.getBitsPerPixel(ImageFormat.NV21) / 8;
			for (int i = 0; i < iCaptureBuffers; i++) {
				mCamera.addCallbackBuffer(new byte[bufSize]);
			}
			// 设置视频输出回调函数，通过AnyChat的外部视频输入接口传入AnyChat内核进行处理
			mCamera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {
					if (data.length != 0 && bNeedCapture) {
						try {
							AnyChatCoreSDK.InputVideoData(data, data.length, 0);
						} catch (Exception e) {

						}
					}
					mCamera.addCallbackBuffer(data);
				}
			});
			mCamera.startPreview(); // 打开预览画面
			bIfPreview = true;

			// 获取设置后的相关参数
			if (mCamera.getParameters().getPreviewFormat() == ImageFormat.NV21)
				mVideoPixfmt = AnyChatDefine.BRAC_PIX_FMT_NV21;
			else if (mCamera.getParameters().getPreviewFormat() == ImageFormat.YV12)
				mVideoPixfmt = AnyChatDefine.BRAC_PIX_FMT_YV12;
			else if (mCamera.getParameters().getPreviewFormat() == ImageFormat.NV16)
				mVideoPixfmt = AnyChatDefine.BRAC_PIX_FMT_NV16;
			else if (mCamera.getParameters().getPreviewFormat() == ImageFormat.YUY2)
				mVideoPixfmt = AnyChatDefine.BRAC_PIX_FMT_YUY2;
			else if (mCamera.getParameters().getPreviewFormat() == ImageFormat.RGB_565)
				mVideoPixfmt = AnyChatDefine.BRAC_PIX_FMT_RGB565;
			else
				Log.e(TAG, "unknow camera privew format:" + mCamera.getParameters().getPreviewFormat());

			Size previewSize = mCamera.getParameters().getPreviewSize();
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_EXTVIDEOINPUT, 1);

			int iCurPreviewRange[] = new int[2];
			parameters.getPreviewFpsRange(iCurPreviewRange);
			AnyChatCoreSDK.SetInputVideoFormat(mVideoPixfmt, previewSize.width, previewSize.height,
					iCurPreviewRange[1] / 1000, 0);
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAMERAFACE, cameraInfo.facing);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class CameraSizeComparator implements Comparator<Size> {
		public int compare(Size lhs, Size rhs) {
			if (lhs.width == rhs.width) {
				if (lhs.height == rhs.height) {
					return 0;
				} else if (lhs.height > rhs.height) {
					return 1;
				} else {
					return -1;
				}
			} else if (lhs.width > rhs.width) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	// 摄像头采集控制
	public void CaptureControl(boolean bCapture) {
		bNeedCapture = bCapture;
		if (bNeedCapture && mVideoPixfmt != -1) {
			try {
				Size previewSize = mCamera.getParameters().getPreviewSize();
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_EXTVIDEOINPUT, 1);
				AnyChatCoreSDK.SetInputVideoFormat(mVideoPixfmt, previewSize.width, previewSize.height,
						mCamera.getParameters().getPreviewFrameRate(), 0);
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAMERAFACE, mCameraFacing);
			} catch (Exception ex) {

			}
		} else {
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_EXTVIDEOINPUT, 0);
		}
	}

	// 关闭摄像头
	public void CloseCamera() {
		try {
			if (null != mCamera) {
				mCamera.stopPreview();
				mCamera.setPreviewCallbackWithBuffer(null);
				bIfPreview = false;
				mVideoPixfmt = -1;
				mCamera.release();
				mCamera = null;
			}
		} catch (Exception ex) {

		}
	}

	// 获取系统中摄像头的数量
	public int GetCameraNumber() {
		try {
			return Camera.getNumberOfCameras();
		} catch (Exception ex) {
			return 0;
		}
	}

	// 自动对焦
	public void CameraAutoFocus() {
		if (mCamera == null || !bIfPreview)
			return;
		try {
			mCamera.autoFocus(null);
		} catch (Exception ex) {

		}
	}

	// 切换摄像头
	public void SwitchCamera() {
		try {
			if (Camera.getNumberOfCameras() == 1 || currentHolder == null)
				return;
			iCurrentCameraId = (iCurrentCameraId == 0) ? 1 : 0;
			if (null != mCamera) {
				mCamera.stopPreview();
				mCamera.setPreviewCallbackWithBuffer(null);
				bIfPreview = false;
				mVideoPixfmt = -1;
				mCamera.release();
				mCamera = null;
			}

			mCamera = Camera.open(iCurrentCameraId);
			mCamera.setPreviewDisplay(currentHolder);
			initCamera();
		} catch (Exception ex) {
			if (null != mCamera) {
				mCamera.release();
				mCamera = null;
				mVideoPixfmt = -1;
			}
		}
	}

	// 根据摄像头的方向选择摄像头（前置、后置）
	public void SelectVideoCapture(int facing) {
		for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == facing) {
				iCurrentCameraId = i;
				break;
			}
		}
	}

	// 根据摄像头的序号选择摄像头（0 - GetCameraNumber()）
	public void SelectCamera(int iCameraId) {
		try {
			if (Camera.getNumberOfCameras() <= iCameraId || currentHolder == null)
				return;
			if (null != mCamera && iCurrentCameraId == iCameraId)
				return;
			iCurrentCameraId = iCameraId;
			if (null != mCamera) {
				mCamera.stopPreview();
				mCamera.setPreviewCallbackWithBuffer(null);
				bIfPreview = false;
				mVideoPixfmt = -1;
				mCamera.release();
				mCamera = null;
			}

			mCamera = Camera.open(iCameraId);
			mCamera.setPreviewDisplay(currentHolder);
			initCamera();
		} catch (Exception ex) {
			if (null != mCamera) {
				mCamera.release();
				mCamera = null;
				mVideoPixfmt = -1;
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera = Camera.open(iCurrentCameraId);
			currentHolder = holder;
			mCamera.setPreviewDisplay(holder);// set the surface to be used for live preview
			initCamera();
		} catch (Exception ex) {
			if (null != mCamera) {
				mCamera.release();
				mCamera = null;
				mVideoPixfmt = -1;
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (null != mCamera) {
			try {
				mCamera.stopPreview();
				mCamera.setPreviewCallbackWithBuffer(null);
				bIfPreview = false;
				mCamera.release();
				mCamera = null;
			} catch (Exception ex) {
				mCamera = null;
				bIfPreview = false;
			}
		}
		currentHolder = null;
		mVideoPixfmt = -1;
	}

	private int getDeviceOrientation() {
		int orientation = 0;
		if (mContext != null) {
			WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			// Log.i(TAG, "wm.getDefaultDisplay().getRotation():" + wm.getDefaultDisplay().getRotation());
			switch (wm.getDefaultDisplay().getRotation()) {
			case Surface.ROTATION_90:
				orientation = 90;
				break;
			case Surface.ROTATION_180:
				orientation = 180;
				break;
			case Surface.ROTATION_270:
				orientation = 270;
				break;
			case Surface.ROTATION_0:
			default:
				orientation = 0;
				break;
			}
		}
		return orientation;
	}

	public void setCameraDisplayOrientation() {
		if (mContext == null)
			return;
		try {
			CameraInfo cameraInfo = new CameraInfo();
			Camera.getCameraInfo(iCurrentCameraId, cameraInfo);

			WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
			int rotation = wm.getDefaultDisplay().getRotation();
			int degrees = 0;
			switch (rotation) {
			case Surface.ROTATION_0:
				degrees = 0;
				break;
			case Surface.ROTATION_90:
				degrees = 90;
				break;
			case Surface.ROTATION_180:
				degrees = 180;
				break;
			case Surface.ROTATION_270:
				degrees = 270;
				break;
			}

			int result;
			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
				result = (cameraInfo.orientation + degrees) % 360;
				result = (360 - result) % 360; // compensate the mirror
			} else { // back-facing
				result = (cameraInfo.orientation - degrees + 360) % 360;
			}

			mCamera.setDisplayOrientation(result);
		} catch (Exception ex) {

		}
	}

}