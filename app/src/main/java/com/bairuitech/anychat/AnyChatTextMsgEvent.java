package com.bairuitech.anychat;

// 文字聊天通知接口
public interface AnyChatTextMsgEvent {
	// 文字消息通知,dwFromUserid表示消息发送者的用户ID号，dwToUserid表示目标用户ID号，可能为-1，表示对大家说，bSecret表示是否为悄悄话
    public void OnAnyChatTextMessage(int dwFromUserid, int dwToUserid, boolean bSecret, String message);
}