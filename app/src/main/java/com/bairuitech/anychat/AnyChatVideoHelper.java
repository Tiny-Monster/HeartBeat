package com.bairuitech.anychat;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import java.nio.ByteBuffer;


//AnyChat 视频显示包装类，实现Java层面的视频播放
public class AnyChatVideoHelper {
	private int MAX_VIDEO_NUM = 10;
	VideoRenderer render[];
	
	public AnyChatVideoHelper() {
		render = new VideoRenderer[MAX_VIDEO_NUM];
	}
	
	public int bindVideo(SurfaceHolder holder) {
		for(int i=0; i<MAX_VIDEO_NUM; i++) {
			if(render[i] != null && render[i].GetUserId() == -1) {
				render[i] = null;
			}
		}
		int index = -1;
		for(int i=0; i<MAX_VIDEO_NUM; i++) {
			if(render[i] == null) {
				render[i] = new VideoRenderer(holder);
				index = i;
				break;
			}
		}
		return index;
	}
	
	public void UnBindVideo(int index) {
		SetVideoUserEx(index, -1, -1);
	}
	
	/**
	 * 设置视频对应的用户信息
	 * @param index 视频显示序号
	 * @param userid 用户ID
	 */
	public void SetVideoUser(int index, int userid) {
		if(index < 0 || index >= MAX_VIDEO_NUM)
			return;
		if(render[index] == null)
			return;
		render[index].SetUserId(userid);
		render[index].SetStreamIndex(0);
	}
	
	/**
	 * 设置视频对应的用户信息扩展方法，支持多路流
	 * @param index 视频显示序号
	 * @param userid 用户ID
	 * @param streamindex 视频流ID
	 */
	public void SetVideoUserEx(int index, int userid, int streamindex) {
		if(index < 0 || index >= MAX_VIDEO_NUM)
			return;
		if(render[index] == null)
			return;
		render[index].SetUserId(userid);
		render[index].SetStreamIndex(streamindex);
	}
	
	public int SetVideoFmt(int userid, int streamindex, int width, int height) {
		VideoRenderer r = GetRenderByUserId(userid, streamindex);
		if(r == null)
			return -1;
		try
		{
			r.CreateBitmap(width, height);
		}
		catch(Exception e)
		{
			
		}
		return 0;
	}
	
	/***
	 * 设置最大裁剪图片的比例。比例越大，当需要裁剪的时候丢失的数据越多，surfaceview占的屏幕也更满
	 * @param scale 比例
	 */
	public void setMaxCutScale(int userId, float scale)
	{
		VideoRenderer r = GetRenderByUserId(userId, 0);
		r.setMaxCutScale(scale);
	}
	
	/***
	 * 设置最大裁剪图片的比例扩展方法，支持多路视频流
	 */
	public void setMaxCutScaleEx(int userId, int streamindex, float scale)
	{
		VideoRenderer r = GetRenderByUserId(userId, streamindex);
		r.setMaxCutScale(scale);
	}
	
	public void ShowVideo(int userid, int streamindex, byte [] mPixel, int rotation, int mirror) {
		VideoRenderer r = GetRenderByUserId(userid, streamindex);
		if(r == null)
			return;
		r.DrawByteBuffer(mPixel, rotation, mirror);
	}
	
	private VideoRenderer GetRenderByUserId(int userid, int streamindex) {
		for(int i=0; i<MAX_VIDEO_NUM; i++) {
			if(render[i] != null) {
				if(render[i].GetUserId() == userid && render[i].GetStreamIndex() == streamindex) {
					return render[i];
				}
			}	
		}
		return null;
	}
	
}


class VideoRenderer implements Callback {
    // the bitmap used for drawing.
    private Bitmap bitmap = null;
    private SurfaceHolder surfaceHolder;
    // Rect of the source bitmap to draw
    private Rect srcRect = new Rect();
    // Rect of the destination canvas to draw to
    private Rect dstRect = new Rect();
    // private float dstTopScale = 0;
    private float dstBottomScale = 1;
    // private float dstLeftScale = 0;
    private float dstRightScale = 1;
    
    private float max_cut_imgscale = 1.0f/3;		//最大能裁剪视频的比例
    
    private int mUserid = -1;
    private int mStreamIndex = -1;
	
	
    public VideoRenderer(SurfaceHolder holder) {
        if(holder == null)
            return;
        mUserid = 0;			// 未知状态
        surfaceHolder = holder;
        holder.addCallback(this);
    }
    
    // 获取当前视频显示单元绑定的用户ID
    public int GetUserId() 				{		return mUserid;			}
    // 获取当前视频显示单元绑定的流ID
    public int GetStreamIndex()			{		return mStreamIndex;	}
    // 设置用户ID
    public void SetUserId(int userid)	{		mUserid = userid;   	}
    // 设置视频流ID
    public void SetStreamIndex(int index){		mStreamIndex = index;	}
    
    // 设置最大裁剪图片的比例
	public void setMaxCutScale(float scale) {
		if(scale>1.0)
			scale=1.0f;
		this.max_cut_imgscale=scale;
	}
    
    // surfaceChanged and surfaceCreated share this function
    private void changeDestRect(int dstWidth, int dstHeight) {
        dstRect.right = (int)(dstRect.left + dstRightScale * dstWidth);
        dstRect.bottom = (int)(dstRect.top + dstBottomScale * dstHeight);
    }
    
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		changeDestRect(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas = holder.lockCanvas();
        if(canvas != null) {
            Rect dst = holder.getSurfaceFrame();
            if(dst != null) {
                changeDestRect(dst.right - dst.left, dst.bottom - dst.top);
            }
            holder.unlockCanvasAndPost(canvas);
        }
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		bitmap = null;
		surfaceHolder = null;
		mUserid = -1;
	}

	public Bitmap CreateBitmap(int width, int height) {
        if (bitmap == null) {
            try {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_DISPLAY);
            }
            catch (Exception e) {
            }
        }
        if(bitmap != null && (srcRect.bottom != height || srcRect.right != width))
        	bitmap = null;
        if(bitmap == null) {
	        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	        srcRect.left = 0;
	        srcRect.top = 0;
	        srcRect.bottom = height;
	        srcRect.right = width;        	
        }
        return bitmap;
    }

    public void SetCoordinates(float left, float top,  float right, float bottom) {
        // dstLeftScale = left;
        // dstTopScale = top;
        dstRightScale = right;
        dstBottomScale = bottom;
    }

    public void DrawByteBuffer(byte [] mPixel, int rotation, int mirror) {
        if(bitmap == null || surfaceHolder == null)
            return;
        ByteBuffer byteBuffer = ByteBuffer.wrap(mPixel); // 将 byte 数组包装到缓冲区中
		byteBuffer.rewind();
		bitmap.copyPixelsFromBuffer(byteBuffer);
		Canvas canvas = surfaceHolder.lockCanvas();
		if (canvas != null) {
			
			Paint paint = new Paint();
			paint.setAntiAlias(true); // 抗锯齿
			Matrix matrix = new Matrix();
			float fScalex = 0;
			float fScaley = 0;
			float transX = 0;
			float transY = 0;
			int c_w = canvas.getWidth();
			int c_h = canvas.getHeight();
			int b_w = bitmap.getWidth();
			int b_h = bitmap.getHeight();
			int temp_b_w = b_w;
			int temp_b_h = b_h;
			if (rotation != 0) {
				matrix.postRotate(rotation, (float)bitmap.getWidth()/2, (float)bitmap.getHeight()/2);
				if (rotation == 90 || rotation == 270) {
					temp_b_w = b_h;
					temp_b_h = b_w;
					matrix.postTranslate((float)(1.0f/2)*(b_h - b_w), (float)(1.0f/2)*(b_w - b_h));
				}
			}
			if (c_h * temp_b_w > c_w * temp_b_h) {
				float cutX=temp_b_w - (float) c_w*temp_b_h/c_h;
				if(cutX>temp_b_w*max_cut_imgscale) {
					cutX=temp_b_w*max_cut_imgscale;
					transY = (c_h - (float) temp_b_h * c_w / (temp_b_w-cutX)) / 2;
				}
				transX=-cutX*c_w/(2*(temp_b_w-cutX));
				fScalex=(float)c_w/(temp_b_w-cutX);
				fScaley=fScalex;
			} else {
				float cutY = temp_b_h - (float) c_h*temp_b_w/c_w;
				if(cutY>temp_b_h*max_cut_imgscale) {
					cutY=(float)temp_b_h*max_cut_imgscale;
					transX = (c_w - (float) temp_b_w * c_h /( temp_b_h-cutY)) / 2;
				}
				transY=-cutY*c_h/(2*(temp_b_h-cutY));
				fScaley = (float) c_h / (temp_b_h-cutY);
				fScalex = fScaley;
			}
			if(mirror != 0) {			
    			matrix.postScale(-fScalex, fScaley);
    			matrix.postTranslate(fScalex*temp_b_w, 0);
    		} else {
    			matrix.postScale(fScalex, fScaley);
    		}
			matrix.postTranslate(transX, transY);
			canvas.drawColor(Color.BLACK);
			canvas.drawBitmap(bitmap, matrix, paint);
			surfaceHolder.unlockCanvasAndPost(canvas);
		} else {
			Log.i("ANYCHAT", "Invalid canvas!");
		}
    }
    
}