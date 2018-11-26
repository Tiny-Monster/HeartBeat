package com.bairuitech.anychat;

public class AnyChatObjectDefine {

	// 对象类型定义
	public static final int ANYCHAT_OBJECT_TYPE_AREA		=	4;		///< 服务区域
	public static final int ANYCHAT_OBJECT_TYPE_QUEUE		=	5;		///< 队列对象
	public static final int ANYCHAT_OBJECT_TYPE_AGENT		=	6;		///< 客服对象
	public static final int ANYCHAT_OBJECT_TYPE_CLIENTUSER	=	8;		///< 客户端用户对象，用于与服务器交换数据

	// 通用标识定义
	public static final int ANYCHAT_OBJECT_FLAGS_CLIENT		=	0;		///< 普通客户
	public static final int ANYCHAT_OBJECT_FLAGS_AGENT		=	2;		///< 坐席用户
	public static final int ANYCHAT_OBJECT_FLAGS_MANANGER	=	4;		///< 管理用户
	public static final int ANYCHAT_OBJECT_FLAGS_AUTOMODE	=	16;		///< 自动服务模式

	public static final int ANYCHAT_INVALID_OBJECT_ID		=	-1;		///< 无效的对象ID

	// 坐席服务状态定义	
	public static final int ANYCHAT_AGENT_STATUS_CLOSEED	=	0;	///< 关闭，不对外提供服务（准备）
	public static final int ANYCHAT_AGENT_STATUS_WAITTING	=	1;	///< 等待中，可随时接受用户服务（示闲）
	public static final int ANYCHAT_AGENT_STATUS_WORKING	=	2;	///< 工作中，正在为用户服务
	public static final int ANYCHAT_AGENT_STATUS_PAUSED		=	3;	///< 暂停服务（示忙）
	public static final int ANYCHAT_AGENT_STATUS_OFFLINE	=	10;	///< 离线


	/**
	 *	对象属性定义
	 */

	// 对象公共信息类型定义
	public static final int ANYCHAT_OBJECT_INFO_FLAGS		=	7;		///< 对象属性标志
	public static final int ANYCHAT_OBJECT_INFO_NAME		=	8;		///< 对象名称
	public static final int ANYCHAT_OBJECT_INFO_PRIORITY	=	9;		///< 对象优先级
	public static final int ANYCHAT_OBJECT_INFO_ATTRIBUTE	=	10;		///< 对象业务属性
	public static final int ANYCHAT_OBJECT_INFO_DESCRIPTION	=	11;		///< 对象描述
	public static final int ANYCHAT_OBJECT_INFO_INTTAG		=	12;		///< 对象标签，整型，上层应用自定义
	public static final int ANYCHAT_OBJECT_INFO_STRINGTAG	=	13;		///< 对象标签，字符串，上层应用自定义
	public static final int ANYCHAT_OBJECT_INFO_GUID		=	14;		///< 对象GUID
	public static final int ANYCHAT_OBJECT_INFO_STATUSJSON	=	15;		///< 对象状态属性集合

	// 服务区域信息类型定义
	public static final int ANYCHAT_AREA_INFO_AGENTCOUNT	=	401;	///< 服务区域客服用户数
	public static final int ANYCHAT_AREA_INFO_GUESTCOUNT	=	402;	///< 服务区域内访客的用户数（没有排入队列的用户）
	public static final int ANYCHAT_AREA_INFO_QUEUEUSERCOUNT=	403;	///< 服务区域内排队的用户数
	public static final int ANYCHAT_AREA_INFO_QUEUECOUNT	=	404;	///< 服务区域内队列的数量
	public static final int ANYCHAT_AREA_INFO_AGENTIDLIST	=	405;	///< 服务区域客服ID列表
	public static final int ANYCHAT_AREA_INFO_IDLEAGENTCOUNT=	406;	///< 服务区域空闲坐席数量
	public static final int ANYCHAT_AREA_INFO_STATUSJSON	=	407;	///< 服务区域状态信息，返回Json数据
	public static final int ANYCHAT_AREA_INFO_WAITINGCOUNT	=	408;	///< 服务区域内等候服务用户数（出了队列，但没有坐席服务的用户）

	// 队列状态信息类型定义
	public static final int ANYCHAT_QUEUE_INFO_MYSEQUENCENO	=	501;	///< 自己在该队列中的序号
	public static final int ANYCHAT_QUEUE_INFO_BEFOREUSERNUM=	502;	///< 排在自己前	面的用户数
	public static final int ANYCHAT_QUEUE_INFO_MYENTERQUEUETIME=503;	///< 进入队列的时间
	public static final int ANYCHAT_QUEUE_INFO_LENGTH		=	504;	///< 队列长度（有多少人在排队），整型
	public static final int ANYCHAT_QUEUE_INFO_WAITTIMESECOND=	508;	///< 自己在队列中的等待时间（排队时长），单位：秒
	public static final int ANYCHAT_QUEUE_INFO_AGENTINFO	=	509;	///< 服务当前队列的坐席信息，返回Json数据

	// 客服状态信息类型定义
	public static final int ANYCHAT_AGENT_INFO_SERVICESTATUS=	601;	///< 服务状态，整型
	public static final int ANYCHAT_AGENT_INFO_SERVICEUSERID=	602;	///< 当前服务的用户ID，整型
	public static final int ANYCHAT_AGENT_INFO_SERVICEBEGINTIME=603;	///< 当前服务的开始时间，整型
	public static final int ANYCHAT_AGENT_INFO_SERVICETOTALTIME=604;	///< 累计服务时间，整型，单位：秒
	public static final int ANYCHAT_AGENT_INFO_SERVICETOTALNUM=	605;	///< 累计服务的用户数，整型
	public static final int ANYCHAT_AGENT_INFO_SERVICEUSERINFO=	606;	///< 当前服务用户信息，字符串
	public static final int ANYCHAT_AGENT_INFO_RELATEQUEUES	=	607;	///< 关联队列List，字符串


	/**
	 *	对象方法定义
	 */

	// 对象公共参数控制常量定义
	public static final int ANYCHAT_OBJECT_CTRL_CREATE		=	2;		///< 创建一个对象
	public static final int ANYCHAT_OBJECT_CTRL_SYNCDATA	=	3;		///< 同步对象数据给指定用户，dwObjectId=-1，表示同步该类型的所有对象
	public static final int ANYCHAT_OBJECT_CTRL_DEBUGOUTPUT	=	4;		///< 对象调试信息输出
	public static final int ANYCHAT_OBJECT_CTRL_DELETE		=	5;		///< 删除对象
	public static final int ANYCHAT_OBJECT_CTRL_MODIFY		=	6;		///< 修改对象信息

	// 服务区域控制常量定义
	public static final int ANYCHAT_AREA_CTRL_USERENTER		=	401;	///< 进入服务区域
	public static final int ANYCHAT_AREA_CTRL_USERLEAVE		=	402;	///< 离开服务区域

	// 队列参数控制常量定义
	public static final int ANYCHAT_QUEUE_CTRL_USERENTER	=	501;	///< 进入队列
	public static final int ANYCHAT_QUEUE_CTRL_USERLEAVE	=	502;	///< 离开队列

	// 客服参数控制常量定义
	public static final int ANYCHAT_AGENT_CTRL_SERVICESTATUS=	601;	///< 坐席服务状态控制（暂停服务、工作中、关闭）
	public static final int ANYCHAT_AGENT_CTRL_SERVICEREQUEST=	602;	///< 服务请求
	public static final int ANYCHAT_AGENT_CTRL_FINISHSERVICE=	604;	///< 结束服务
	public static final int ANYCHAT_AGENT_CTRL_EVALUATION	=	605;	///< 服务评价，wParam为客服userid，lParam为评分，lpStrValue为留言



	/**
	 *	对象异步事件定义
	 */

	// 对象公共事件常量定义
	public static final int ANYCHAT_OBJECT_EVENT_UPDATE			= 1;	///< 对象数据更新
	public static final int ANYCHAT_OBJECT_EVENT_SYNCDATAFINISH = 2;	///< 对象数据同步结束

	// 服务区域事件常量定义
	public static final int ANYCHAT_AREA_EVENT_STATUSCHANGE	=	401;	///< 服务区域状态变化
	public static final int ANYCHAT_AREA_EVENT_ENTERRESULT	=	402;	///< 进入服务区域结果
	public static final int ANYCHAT_AREA_EVENT_USERENTER	=	403;	///< 用户进入服务区域
	public static final int ANYCHAT_AREA_EVENT_USERLEAVE	=	404;	///< 用户离开服务区域
	public static final int ANYCHAT_AREA_EVENT_LEAVERESULT	=	405;	///< 离开服务区域结果

	// 队列事件常量定义
	public static final int ANYCHAT_QUEUE_EVENT_STATUSCHANGE=	501;	///< 队列状态变化
	public static final int ANYCHAT_QUEUE_EVENT_ENTERRESULT	=	502;	///< 进入队列结果
	public static final int ANYCHAT_QUEUE_EVENT_USERENTER	=	503;	///< 用户进入队列
	public static final int ANYCHAT_QUEUE_EVENT_USERLEAVE	=	504;	///< 用户离开队列
	public static final int ANYCHAT_QUEUE_EVENT_LEAVERESULT	=	505;	///< 离开队列结果

	// 坐席事件常量定义
	public static final int ANYCHAT_AGENT_EVENT_STATUSCHANGE=	601;	///< 坐席状态变化
	public static final int ANYCHAT_AGENT_EVENT_SERVICENOTIFY=	602;	///< 坐席服务通知（哪个用户到哪个客服办理业务）
	public static final int ANYCHAT_AGENT_EVENT_WAITINGUSER	=	603;	///< 暂时没有客户，请等待
	public static final int ANYCHAT_AGENT_EVENT_ISREADY		=	604;	///< 坐席准备好，可以发起呼叫
	
	 
}