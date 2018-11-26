package com.bairuitech.anychat;

// AnyChat私聊消息通知接口
public interface AnyChatPrivateChatEvent {
	// 用户私聊请求消息，dwUserId表示发起者的用户ID号，dwRequestId表示私聊请求编号，标识该请求
    public void OnAnyChatPrivateRequestMessage(int dwUserId, int dwRequestId);
	// 用户私聊请求回复消息，dwUserId表示回复者的用户ID号，dwErrorCode为出错代码
    public void OnAnyChatPrivateEchoMessage(int dwUserId, int dwErrorCode);
	// 用户退出私聊消息，dwUserId表示退出者的用户ID号，dwErrorCode为出错代码
    public void OnAnyChatPrivateExitMessage(int dwUserId, int dwErrorCode);	
}