package com.bairuitech.anychat;

// AnyChat视频录制事件通知接口
public interface AnyChatRecordEvent {
    // 视频录制完成事件
	public void OnAnyChatRecordEvent(int dwUserId, int dwErrorCode, String lpFileName, int dwElapse, int dwFlags, int dwParam, String lpUserStr);
	// 图像抓拍完成事件
	public void OnAnyChatSnapShotEvent(int dwUserId, int dwErrorCode, String lpFileName, int dwFlags, int dwParam, String lpUserStr);
}