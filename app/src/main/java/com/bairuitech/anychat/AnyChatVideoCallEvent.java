package com.bairuitech.anychat;

// AnyChat视频呼叫事件通知接口
public interface AnyChatVideoCallEvent {
    public void OnAnyChatVideoCallEvent(int dwEventType, int dwUserId, int dwErrorCode, int dwFlags, int dwParam, String userStr);
}