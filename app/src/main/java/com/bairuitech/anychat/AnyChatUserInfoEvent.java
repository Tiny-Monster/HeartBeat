package com.bairuitech.anychat;

//AnyChat用户信息事件接口
public interface AnyChatUserInfoEvent {
	// 用户信息更新通知，wParam（INT）表示用户ID号，lParam（INT）表示更新类别
    public void OnAnyChatUserInfoUpdate(int dwUserId, int dwType);
	// 好友在线状态变化，wParam（INT）表示好友用户ID号，lParam（INT）表示用户的当前活动状态：0 离线， 1 上线
    public void OnAnyChatFriendStatus(int dwUserId, int dwStatus);
}
