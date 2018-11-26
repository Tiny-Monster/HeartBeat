package com.bairuitech.anychat;

// 数据加密、解密接口
public interface AnyChatDataEncDecEvent {

	// 数据加（解）密标志定义（DataEncDec回调参数）
	public static final int BRAC_DATAENCDEC_FLAGS_ENCMODE	=	0x01;	///< 加密模式
	public static final int BRAC_DATAENCDEC_FLAGS_DECMODE	=	0x02;	///< 解密模式
	public static final int BRAC_DATAENCDEC_FLAGS_AUDIO		=	0x10;	///< 音频编码数据
	public static final int BRAC_DATAENCDEC_FLAGS_VIDEO		=	0x20;	///< 视频编码数据
	public static final int BRAC_DATAENCDEC_FLAGS_BUFFER	=	0x40;	///< 透明通道数据
	public static final int BRAC_DATAENCDEC_FLAGS_TXTMSG	=	0x80;	///< 文字聊天数据

	// 数据加密、解密回调函数定义
	public int OnAnyChatDataEncDec(int userid, int flags, byte[] lpBuf, int dwLen, AnyChatOutParam outParam);
}
