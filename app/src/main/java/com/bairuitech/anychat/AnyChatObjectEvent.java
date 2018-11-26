package com.bairuitech.anychat;

// AnyChat业务对象事件通知接口
public interface AnyChatObjectEvent {
    public void OnAnyChatObjectEvent(int dwObjectType, int dwObjectId, int dwEventType, int dwParam1, int dwParam2, int dwParam3, int dwParam4, String strParam);
}