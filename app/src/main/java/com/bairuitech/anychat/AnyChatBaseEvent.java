package com.bairuitech.anychat;

//AnyChat基本事件接口
public interface AnyChatBaseEvent {
	// 连接服务器消息, bSuccess表示是否连接成功
    public void OnAnyChatConnectMessage(boolean bSuccess);
	// 用户登录消息，dwUserId表示自己的用户ID号，dwErrorCode表示登录结果：0 成功，否则为出错代码
    public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode);
	// 用户进入房间消息，dwRoomId表示所进入房间的ID号，dwErrorCode表示是否进入房间：0成功进入，否则为出错代码
    public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode);
	// 房间在线用户消息，进入房间后触发一次，dwUserNum表示在线用户数（包含自己），dwRoomId表示房间ID
    public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId);
	// 用户进入/退出房间消息，dwUserId表示用户ID号，bEnter表示该用户是进入（TRUE）或离开（FALSE）房间
    public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter);
	//网络断开消息，该消息只有在客户端连接服务器成功之后，网络异常中断之时触发，dwErrorCode表示连接断开的原因
    public void OnAnyChatLinkCloseMessage(int dwErrorCode);	
	
}
