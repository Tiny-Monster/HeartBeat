package com.bairuitech.anychat;

// AnyChat Core SDK事件通知接口
public interface AnyChatCoreSDKEvent {
    public void OnAnyChatCoreSDKEvent(int dwEventType, String szJsonStr);
}