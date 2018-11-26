package com.bairuitech.anychat;

public class AnyChatDefine {
	// 消息通知类型定义
	static final int WM_GV = 1224;
	static final int WM_GV_CONNECT			=	WM_GV + 1;
	static final int WM_GV_LOGINSYSTEM		=	WM_GV + 2;
	static final int WM_GV_ENTERROOM		=	WM_GV + 3;
	static final int WM_GV_MICSTATECHANGE	=	WM_GV + 4;
	static final int WM_GV_USERATROOM		=	WM_GV + 5;
	static final int WM_GV_LINKCLOSE		=	WM_GV + 6;
	static final int WM_GV_ONLINEUSER		=	WM_GV + 7;
	static final int WM_GV_CAMERASTATE		=	WM_GV + 11;
	static final int WM_GV_CHATMODECHG		=	WM_GV + 12;
	static final int WM_GV_ACTIVESTATE		=	WM_GV + 13;
	static final int WM_GV_P2PCONNECTSTATE	=	WM_GV + 14;
	static final int WM_GV_VIDEOSIZECHG		=	WM_GV + 15;
	static final int WM_GV_USERINFOUPDATE	=	WM_GV + 16;
	static final int WM_GV_FRIENDSTATUS		=	WM_GV + 17;
	static final int WM_GV_PRIVATEREQUEST	=	WM_GV + 21;
	static final int WM_GV_PRIVATEECHO		=	WM_GV + 22;
	static final int WM_GV_PRIVATEEXIT		=	WM_GV + 23;
	static final int WM_GV_AUDIOPLAYCTRL	=	WM_GV + 100;
	static final int WM_GV_AUDIORECCTRL		=	WM_GV + 101;
	static final int WM_GV_VIDEOCAPCTRL		=	WM_GV + 102;
	
	// 视频图像格式定义
	public static final int BRAC_PIX_FMT_RGB24		= 	0;	///< Packed RGB 8:8:8, 24bpp, RGBRGB...（MEDIASUBTYPE_RGB24）
	public static final int BRAC_PIX_FMT_RGB32		=	1;	///< 对应于：MEDIASUBTYPE_RGB32，Packed RGB 8:8:8, 32bpp, (msb)8A 8R 8G 8B(lsb), in cpu endianness
	public static final int BRAC_PIX_FMT_YV12		=	2;	///< 对应于：MEDIASUBTYPE_YV12，Planar YUV 4:2:0, 12bpp, (1 Cr & Cb sample per 2x2 Y samples)
	public static final int BRAC_PIX_FMT_YUY2		=	3;	///< 对应于：MEDIASUBTYPE_YUY2，Packed YUV 4:2:2, 16bpp, Y0 Cb Y1 Cr
	public static final int BRAC_PIX_FMT_YUV420P	=	4;	///< Planar YUV 4:2:0, 12bpp, (1 Cr & Cb sample per 2x2 Y samples)
	public static final int BRAC_PIX_FMT_RGB565		=	5;	///< 对应于：MEDIASUBTYPE_RGB565
	public static final int BRAC_PIX_FMT_RGB555		=	6;	///< 对应于：MEDIASUBTYPE_RGB555
	public static final int BRAC_PIX_FMT_NV12		=	7;	///< Planar YUV 4:2:0, 12bpp, Two arrays, one is all Y, the other is U and V
	public static final int BRAC_PIX_FMT_NV21		=	8;	///< Planar YUV 4:2:0, 12bpp, Two arrays, one is all Y, the other is V and U
	public static final int BRAC_PIX_FMT_NV16		=	9;	///< YUV422SP
	
	// 视频采集驱动
	public static final int VIDEOCAP_DRIVER_JAVA	=	3;	///< Java视频采集驱动
	// 视频显示驱动
	public static final int VIDEOSHOW_DRIVER_JAVA	=	5;	///< Java视频显示驱动
	// 音频采集驱动
	public static final int AUDIOREC_DRIVER_JAVA	=	3;	///< Java音频采集驱动
	// 音频播放驱动
	public static final int AUDIOPLAY_DRIVER_JAVA	=	3;	///< Java音频播放驱动
	
	// 内核参数定义
	public static final int BRAC_SO_AUDIO_VADCTRL			=	1;	///< 音频静音检测控制（参数为：int型：1打开，0关闭）
	public static final int BRAC_SO_AUDIO_NSCTRL			=	2;	///< 音频噪音抑制控制（参数为：int型：1打开，0关闭）
	public static final int BRAC_SO_AUDIO_ECHOCTRL			=	3;	///< 音频回音消除控制（参数为：int型：1打开，0关闭）
	public static final int BRAC_SO_AUDIO_AGCCTRL			=	4;	///< 音频自动增益控制（参数为：int型：1打开，0关闭）
	public static final int BRAC_SO_AUDIO_CPATUREMODE		=	5;	///< 音频采集模式设置（参数为：int型：0 发言模式，1 放歌模式，2 卡拉OK模式，3 线路输入模式）
	public static final int BRAC_SO_AUDIO_MICBOOST			=	6;	///< 音频采集Mic增强控制（参数为：int型：0 取消，1 选中，2 设备不存在[查询时返回值]）
	public static final int BRAC_SO_AUDIO_AUTOPARAM			=	7;	///< 根据音频采集模式，自动选择合适的相关参数，包括编码器、采样参数、码率参数等（参数为int型：1 启用，0 关闭[默认]）
	public static final int BRAC_SO_AUDIO_MONOBITRATE		=	8;	///< 设置单声道模式下音频编码目标码率（参数为：int型，单位：bps）
	public static final int BRAC_SO_AUDIO_STEREOBITRATE		=	9;	///< 设置双声道模式下音频编码目标码率（参数为：int型，单位：bps）
	public static final int BRAC_SO_AUDIO_PLAYDRVCTRL		=	70;	///< 音频播放驱动选择（参数为：int型，0默认驱动， 1 DSound驱动， 2 WaveOut驱动， 3 Java播放[Android平台使用]）
	public static final int BRAC_SO_AUDIO_SOFTVOLMODE		=	73;	///< 设置软件音量模式控制（参数为int型，1打开，0关闭[默认]），使用软件音量模式，将不会改变系统的音量设置
	public static final int BRAC_SO_AUDIO_RECORDDRVCTRL		=	74;	///< 音频采集驱动控制（参数为int型，0默认驱动， 1 DSound驱动， 2 WaveIn驱动， 3 Java采集[Android平台使用]）
	public static final int BRAC_SO_AUDIO_ECHODELAY			=	75;	///< 音频回声消除延迟参数设置（参数为int型，单位：ms）
	public static final int BRAC_SO_AUDIO_NSLEVEL			=	76;	///< 音频噪音抑制水平参数设置（参数为int型，0 - 3，默认为2，值越大抑制水平越高，抑制噪音的能力越强）
	
	public static final int BRAC_SO_RECORD_VIDEOBR			=	10;	///< 录像视频码率设置（参数为：int型，单位：bps）
	public static final int BRAC_SO_RECORD_AUDIOBR			=	11;	///< 录像音频码率设置（参数为：int型，单位：bps）
	public static final int BRAC_SO_RECORD_TMPDIR			=	12;	///< 录像文件临时目录设置（参数为字符串TCHAR类型，必须是完整的绝对路径）
	public static final int BRAC_SO_SNAPSHOT_TMPDIR			=	13;	///< 快照文件临时目录设置（参数为字符串TCHAR类型，必须是完整的绝对路径）
	public static final int BRAC_SO_RECORD_FILETYPE			=	140;///< 录制文件类型设置（参数为：int型， 0 MP4[默认], 1 WMV, 2 FLV, 3 MP3）
	public static final int BRAC_SO_RECORD_WIDTH			=	141;///< 录制视频宽度设置（参数为：int型，如：320）
	public static final int BRAC_SO_RECORD_HEIGHT			=	142;///< 录制文件高度设置（参数为：int型，如：240）
	public static final int BRAC_SO_RECORD_FILENAMERULE		=	143;///< 录制文件名命名规则（参数为：int型）
	public static final int BRAC_SO_RECORD_CLIPMODE			=	144;///< 录制视频裁剪模式（参数为：int型）
	public static final int BRAC_SO_RECORD_DISABLEDATEDIR	=	145;///< 录制文件不按日期分目录保存，全部生成在指定文件夹中（参数为：int型， 0禁止[默认] 1 开启）
	public static final int BRAC_SO_RECORD_INSERTIMAGE		=	146;///< 录制过程中插入图片，Json字符串参数
	
	public static final int BRAC_SO_CORESDK_TMPDIR			=	14;	///< 设置AnyChat Core SDK临时目录（参数为字符串TCHAR类型，必须是完整的绝对路径）
	public static final int BRAC_SO_CORESDK_LOADCODEC		=	16;	///< 加载外部编解码器（参数为字符串TCHAR类型，必须是完整的绝对路径，包含文件名，或包含文件名的绝对路径）
	public static final int BRAC_SO_CORESDK_USEARMV6LIB		=	17;	///< 强制使用ARMv6指令集的库，android平台使用（参数为：int型，1使用ARMv6指令集， 0内核自动判断[默认]）
	public static final int BRAC_SO_CORESDK_USEHWCODEC		=	18;	///< 使用平台内置硬件编解码器（参数为int型，0 不使用硬件编解码器[默认]  1 使用内置硬件编解码器）
	
	public static final int BRAC_SO_CORESDK_PATH			=	20;	///< 设置AnyChat Core SDK相关组件路径（参数为字符串TCHAR类型，必须是完整的绝对路径）
	public static final int BRAC_SO_CORESDK_DUMPCOREINFO	=	21;	///< 输出内核信息到日志文件中，便于分析故障原因（参数为：int型：1 输出）
	public static final int BRAC_SO_CORESDK_MAINVERSION		=	22;	///< 查询SDK主版本号号（参数为int型）
	public static final int BRAC_SO_CORESDK_SUBVERSION		=	23;	///< 查询SDK从版本号（参数为int型）
	public static final int BRAC_SO_CORESDK_BUILDTIME		=	24;	///< 查询SDK编译时间（参数为字符串TCHAR类型）
	public static final int BRAC_SO_CORESDK_EXTVIDEOINPUT	=	26;	///< 外部扩展视频输入控制（参数为int型， 0 关闭外部视频输入[默认]， 1 启用外部视频输入）
	public static final int BRAC_SO_CORESDK_EXTAUDIOINPUT	=	27;	///< 外部扩展音频输入控制（参数为int型， 0 关闭外部音频输入[默认]， 1 启用外部音频输入）
	public static final int BRAC_SO_CORESDK_LOWDELAYCTRL	=	28;	///< 低延迟模式控制（参数为int型，0 关闭低延迟模式[默认]， 1 启用低延迟模式）
	public static final int BRAC_SO_CORESDK_NEWGUID			=	29;	///< 产生新的GUID字符串
	
	public static final int BRAC_SO_LOCALVIDEO_BITRATECTRL	=	30;	///< 本地视频编码码率设置（参数为int型，单位bps，同服务器配置：VideoBitrate）
	public static final int BRAC_SO_LOCALVIDEO_QUALITYCTRL	=	31;	///< 本地视频编码质量因子控制（参数为int型，同服务器配置：VideoQuality）
	public static final int BRAC_SO_LOCALVIDEO_GOPCTRL		=	32;	///< 本地视频编码关键帧间隔控制（参数为int型，同服务器配置：VideoGOPSize）
	public static final int BRAC_SO_LOCALVIDEO_FPSCTRL		=	33;	///< 本地视频编码帧率控制（参数为int型，同服务器配置：VideoFps）
	public static final int BRAC_SO_LOCALVIDEO_PRESETCTRL	=	34;	///< 本地视频编码预设参数控制（参数为int型，1-5）
	public static final int BRAC_SO_LOCALVIDEO_APPLYPARAM	=	35;	///< 应用本地视频编码参数，使得前述修改即时生效（参数为int型：1 使用新参数，0 使用默认参数）
	public static final int BRAC_SO_LOCALVIDEO_VIDEOSIZEPOLITIC=36;	///< 本地视频采集分辩率控制策略（参数为int型，0 自动向下逐级匹配[默认]；1 使用采集设备默认分辩率），当配置的分辩率不被采集设备支持时有效
	public static final int BRAC_SO_LOCALVIDEO_DEINTERLACE	=	37;	///< 本地视频反交织参数控制（参数为int型： 0 不进行反交织处理[默认]；1 反交织处理），当输入视频源是隔行扫描源（如电视信号）时通过反交织处理可以提高画面质量
	public static final int BRAC_SO_LOCALVIDEO_WIDTHCTRL	=	38;	///< 本地视频采集分辨率宽度控制（参数为int型，同服务器配置：VideoWidth）
	public static final int BRAC_SO_LOCALVIDEO_HEIGHTCTRL	=	39;	///< 本地视频采集分辨率高度控制（参数为int型，同服务器配置：VideoHeight）
	public static final int BRAC_SO_LOCALVIDEO_FOCUSCTRL	=	90;	///< 本地视频摄像头对焦控制（参数为int型，1表示自动对焦， 0表示手动对焦）
	public static final int BRAC_SO_LOCALVIDEO_PIXFMTCTRL	=	91;	///< 本地视频采集优先格式控制（参数为int型，-1表示智能匹配，否则优先采用指定格式，参考：BRAC_PixelFormat）
	public static final int BRAC_SO_LOCALVIDEO_OVERLAY		=	92;	///< 本地视频采用Overlay模式（参数为int型，1表示采用Overlay模式， 0表示普通模式[默认]）
	public static final int BRAC_SO_LOCALVIDEO_CODECID		=	93;	///< 本地视频编码器ID设置（参数为int型，-1表示默认，如果设置的编码器ID不存在，则内核会采用默认的编码器）
	public static final int BRAC_SO_LOCALVIDEO_ROTATECTRL	=	94;	///< 本地视频旋转控制（参数为int型，0表示不进行旋转，1表示垂直翻转）
	public static final int BRAC_SO_LOCALVIDEO_CAPDRIVER	=	95;	///< 本地视频采集驱动设置（参数为int型，0表示自动选择[默认]， 1 Video4Linux, 2 DirectShow, 3 Java采集[Android平台使用]）
	public static final int BRAC_SO_LOCALVIDEO_FIXCOLORDEVIA=	96;	///< 修正视频采集颜色偏色（参数为int型，0表示关闭[默认]，1 开启）
	public static final int BRAC_SO_LOCALVIDEO_ORIENTATION	=	97;	///< 本地视频设备方向（参数为：int型，定义为常量：ANYCHAT_DEVICEORIENTATION_XXXX）
	public static final int BRAC_SO_LOCALVIDEO_AUTOROTATION	=	98;	///< 本地视频自动旋转控制（参数为int型， 0表示关闭， 1 开启[默认]，视频旋转时需要参考本地视频设备方向参数）
	public static final int BRAC_SO_LOCALVIDEO_SURFACEROTATION=	99;	///< 设置本地视频预览显示旋转角度（参数为int型，角度）
	public static final int BRAC_SO_LOCALVIDEO_CAMERAFACE	=	100;///< 本地摄像头方向（前置、后置）
	public static final int BRAC_SO_LOCALVIDEO_DEVICEMODE	=	103;///< 设备类型
	public static final int BRAC_SO_LOCALVIDEO_OVERLAYTIMESTAMP=105;///< 迭加时间戳到本地视频（参数为：int型， 0 不迭加[默认]， 1 迭加）
	public static final int BRAC_SO_LOCALVIDEO_DEVICENAME	=	106;///< 本地视频采集设备名称，用于设置打开指定摄像头设备（参数为字符串类型）
	public static final int BRAC_SO_LOCALVIDEO_CLIPMODE		=	107;///< 本地视频裁剪模式（参数为int型， 0 自动[默认]，禁止自动旋转时有效）
	public static final int BRAC_SO_LOCALVIDEO_SCREENHWND	=	108;///< 屏幕采集窗口句柄
	public static final int BRAC_SO_LOCALVIDEO_SCREENFLAGS	=	109;///< 屏幕采集标志（参数为int型）
	public static final int BRAC_SO_LOCALVIDEO_VIRTUALBK	=	111;///< 本地视频迭加虚拟背景（字符串类型，JSON格式，包括虚拟背景路径、是否开启以及其它参数项）
	
	public static final int BRAC_SO_NETWORK_P2PPOLITIC		=	40;	///< 本地网络P2P策略控制（参数为：int型：0 禁止本地P2P，1 服务器控制P2P[默认]，2 上层应用控制P2P连接，3 按需建立P2P连接）
	public static final int BRAC_SO_NETWORK_P2PCONNECT		=	41;	///< 尝试与指定用户建立P2P连接（参数为int型，表示目标用户ID），连接建立成功后，会通过消息反馈给上层应用，P2P控制策略=2时有效
	public static final int BRAC_SO_NETWORK_P2PBREAK		=	42;	///< 断开与指定用户的P2P连接（参数为int型，表示目标用户ID）[暂不支持，保留]
	public static final int BRAC_SO_NETWORK_TCPSERVICEPORT	=	43;	///< 设置本地TCP服务端口（参数为int型），连接服务器之前设置有效
	public static final int BRAC_SO_NETWORK_UDPSERVICEPORT	=	44;	///< 设置本地UDP服务端口（参数为int型），连接服务器之前设置有效
	public static final int BRAC_SO_NETWORK_MULTICASTPOLITIC=	45;	///< 组播策略控制（参数为int型：0 执行服务器路由策略，禁止组播发送[默认]， 1 忽略服务器路由策略，只向组播组广播媒体流， 2 执行服务器路由策略，同时组播）
	public static final int BRAC_SO_NETWORK_TRANSBUFMAXBITRATE=	46;	///< 传输缓冲区、文件最大码率控制（参数为int型，0 不限制，以最快速率传输[默认]， 否则表示限制码率，单位为：bps）
	public static final int BRAC_SO_NETWORK_AUTORECONNECT	=	47;	///< 网络掉线自动重连功能控制（参数为int型，0 关闭， 1 开启[默认]）
	public static final int BRAC_SO_NETWORK_MTUSIZE			=	48;	///< 设置网络层MTU大小（参数为int型）
	public static final int BRAC_SO_NETWORK_UDPSTATUS		=	49;	///< UDP网络通信状态查询（参数为int型）
	public static final int BRAC_SO_NETWORK_LARGEDELAY		=	53;	///< 网络高延迟模式，适用于卫星网络环境（参数为int型）
	public static final int BRAC_SO_NETWORK_IPV6DNS			=	54;	///< IPv6域名解析控制（参数为int型，0 关闭， 1开启[默认]），传统网络（IPv4）下，禁用IPv6可提高域名解析速度

	public static final int BRAC_SO_PROXY_FUNCTIONCTRL		=	50;	///< 本地用户代理功能控制，（参数为：int型，1启动代理，0关闭代理[默认]）
	public static final int BRAC_SO_PROXY_VIDEOCTRL			=	51;	///< 本地用户代理视频控制，将本地视频变为指定用户的视频对外发布（参数为int型，表示其它用户的userid）
	public static final int BRAC_SO_PROXY_AUDIOCTRL			=	52;	///< 本地用户代理音频控制，将本地音频变为指定用户的音频对外发布（参数同BRAC_SO_PROXY_VIDEOCTRL）

	public static final int BRAC_SO_STREAM_BUFFERTIME		=	60;	///< 流缓冲时间（参数为int型，单位：毫秒，取值范围：500 ~ 5000，默认：800）
	public static final int BRAC_SO_STREAM_SMOOTHPLAYMODE	=	61;	///< 平滑播放模式（参数为int型，0 关闭[默认], 1 打开），打开状态下遇到视频丢帧时会继续播放（可能出现马赛克），不会卡住
	
	public static final int BRAC_SO_VIDEOSHOW_MODECTRL		=	80;	///< 视频显示模式控制（参数为：int型，0 单画面显示，1 视频迭加显示）
	public static final int BRAC_SO_VIDEOSHOW_SETPRIMARYUSER=	81;	///< 设置主显示用户编号（参数为：int型，用户ID号）
	public static final int BRAC_SO_VIDEOSHOW_SETOVERLAYUSER=	82;	///< 设置迭加显示用户编号（参数为：int型，用户ID号）
	public static final int BRAC_SO_VIDEOSHOW_DRIVERCTRL	=	83;	///< 视频显示驱动控制（参数为：int型，0 默认驱动， 1 Windows DirectShow，2 Windows GDI，3 SDL, 4 Android2X, 5 Android Java）
	public static final int BRAC_SO_VIDEOSHOW_GPUDIRECTRENDER =	84;	///< 视频数据经过GPU直接渲染，将解码后的视频数据直接传输到GPU的物理地址（参数为：int型， 0 关闭[默认]， 1 打开），与硬件平台相关
	public static final int BRAC_SO_VIDEOSHOW_AUTOROTATION	=	85;	///< 远程视频显示自动旋转控制（参数为int型， 0表示关闭， 1 开启[默认]，视频旋转时需要参考本地视频设备方向参数）
	public static final int BRAC_SO_VIDEOSHOW_CLIPMODE		=	86;	///< 远程视频显示旋转裁剪模式（参数为int型， 0 自动[默认]）

	public static final int BRAC_SO_CORESDK_DEVICEMODE		=	130; ///< 设备模式控制（局域网设备之间可以互相通信，不依赖服务器；参数为int型，0 关闭[默认]，1 开启）
	public static final int BRAC_SO_CORESDK_SCREENCAMERACTRL=	131; ///< 桌面共享功能控制（参数为：int型， 0 关闭[默认]， 1 开启）
	public static final int BRAC_SO_CORESDK_DATAENCRYPTION	=	132; ///< 数据加密控制（参数为：int型， 0 关闭[默认]， 1 开启）
	public static final int BRAC_SO_CORESDK_UPLOADLOGINFO	=	134; ///< 上传日志信息到服务器（参数为：int型，0 关闭[默认]， 1 开启）
	public static final int BRAC_SO_CORESDK_WRITELOG		=	135; ///< 写入调试信息到客户端日志文件中
	public static final int BRAC_SO_CORESDK_NEWLOGFILE		=	136; ///< 产生新的日志文件
	public static final int BRAC_SO_CORESDK_SUPPORTVIDEOCODEC =	210; ///< 设置支持的视频编码器
	public static final int BRAC_SO_CORESDK_SUPPORTAUDIOCODEC =	211; ///< 设置支持的音频编码器
	public static final int BRAC_SO_CORESDK_DISABLEMEDIACONSUL=	212; ///< 禁止媒体协商
	public static final int BRAC_SO_CORESDK_QUERYTIMEOUTTIME=	213; ///< 信息查询超时时间（参数为：int型，单位：ms，默认值1000）
	public static final int BRAC_SO_CORESDK_REMOTEASSISTHWND=	214; ///< 远程协助窗口句柄
	public static final int BRAC_SO_CORESDK_REMOTEASSISTXPOS=	215; ///< 远程协助窗口滚动条位置（X）
	public static final int BRAC_SO_CORESDK_REMOTEASSISTYPOS=	216; ///< 远程协助窗口滚动条位置（Y）
	public static final int BRAC_SO_CORESDK_FITTENCENTLIVE	=	217; ///< 兼容腾讯视频直播SDK
	public static final int BRAC_SO_CORESDK_DFCFLIVE		=	218;
	public static final int BRAC_SO_CORESDK_DISABLEDNSCONNECT =	219; ///< 屏蔽DNS寻址
	public static final int BRAC_SO_CORESDK_LOGFILEROOTPATH	=	220; ///< 日志文件保存根路径（日志重定向，参数为字符串，绝对路径）
	public static final int BRAC_SO_CORESDK_LOGFILERULE		=	221; ///< 客户端日志文件保存规则（参数为int型，0 自动覆盖[默认] 1 按日期保存，不覆盖）
	
	public static final int BRAC_SO_UDPTRACE_MODE			=	160; ///< UDP数据包跟踪模式
	public static final int BRAC_SO_UDPTRACE_PACKSIZE		=	161; ///< UDP数据包跟踪的大小，单位：BYTE
	public static final int BRAC_SO_UDPTRACE_BITRATE		=	162; ///< UDP数据包跟踪的包速率，单位：bps
	public static final int BRAC_SO_UDPTRACE_START			=	163; ///< UDP数据包跟踪控制（参数为int型，1 启动， 0 停止）
	public static final int BRAC_SO_UDPTRACE_LOCALRECVNUM	=	164; ///< UDP数据包跟踪本地接收包数量
	public static final int BRAC_SO_UDPTRACE_SERVERRECVNUM	=	165; ///< UDP数据包跟踪服务器接收包数量
	public static final int BRAC_SO_UDPTRACE_SOURCESENDNUM	=	166; ///< UDP数据包跟踪源发包数量
	public static final int BRAC_SO_UDPTRACE_SENDUSERID		=	167; ///< UDP数据包跟踪源用户ID

	public static final int BRAC_SO_OBJECT_INITFLAGS		=	200; ///< 业务对象身份初始化
	public static final int BRAC_SO_CLOUD_APPGUID			=	300; ///< 云平台应用GUID（参数为：字符串类型，连接服务器之前设置）
	
	// 传输任务信息参数定义
	public static final int BRAC_TRANSTASK_PROGRESS			=	1;	///< 传输任务进度查询（参数为：int型（0 ~ 100））
	public static final int BRAC_TRANSTASK_BITRATE			=	2;	///< 传输任务当前传输码率（参数为：int型，单位：bps）
	public static final int BRAC_TRANSTASK_STATUS			=	3;	///< 传输任务当前状态（参数为：int型）


	// 录像功能标志定义（API：StreamRecordCtrl传入参数，目前Android平台暂时只支持服务器端录制）
	public static final int BRAC_RECORD_FLAGS_VIDEO			=	0x00000001;	///< 录制视频
	public static final int BRAC_RECORD_FLAGS_AUDIO			=	0x00000002;	///< 录制音频
	public static final int BRAC_RECORD_FLAGS_SERVER		=	0x00000004;	///< 服务器端录制
	public static final int BRAC_RECORD_FLAGS_MIXAUDIO		=	0x00000010;	///< 录制音频时，将其它人的声音混音后录制
	public static final int BRAC_RECORD_FLAGS_MIXVIDEO		=	0x00000020;	///< 录制视频时，将其它人的视频迭加后录制（画中画模式）
	public static final int BRAC_RECORD_FLAGS_ABREAST		=	0x00000100;	///< 录制视频时，将其它人的视频并列录制
	public static final int BRAC_RECORD_FLAGS_STEREO		=	0x00000200;	///< 录制音频时，将其它人的声音混合为立体声后录制
	public static final int BRAC_RECORD_FLAGS_STREAM	    =   0x00001000;	///< 对视频流进行录制（效率高，但可能存在视频方向旋转的问题）
	public static final int BRAC_RECORD_FLAGS_USERFILENAME	=	0x00002000; ///< 用户自定义文件名


	// 用户状态标志定义（API：BRAC_QueryUserState 传入参数）
	public static final int BRAC_USERSTATE_CAMERA			=	1;	///< 用户摄像头状态（参数为DWORD型）
	public static final int BRAC_USERSTATE_HOLDMIC			=	2;	///< 用户音频设备状态（参数为DWORD型，返回值：0 音频采集关闭， 1 音频采集开启）
	public static final int BRAC_USERSTATE_SPEAKVOLUME		=	3;	///< 用户当前说话音量（参数为DOUBLE类型（0.0 ~ 100.0））
	public static final int BRAC_USERSTATE_RECORDING		=	4;	///< 用户录像（音）状态（参数为DWORD型）
	public static final int BRAC_USERSTATE_LEVEL			=	5;	///< 用户级别（参数为DWORD型）
	public static final int BRAC_USERSTATE_NICKNAME			=	6;	///< 用户昵称（参数为字符串TCHAR类型）
	public static final int BRAC_USERSTATE_LOCALIP			=	7;	///< 用户本地IP地址（内网，参数为字符串TCHAR类型）
	public static final int BRAC_USERSTATE_INTERNETIP		=	8;	///< 用户互联网IP地址（参数为字符串TCHAR类型）
	public static final int BRAC_USERSTATE_VIDEOBITRATE		=	9;	///< 用户当前的视频码率（参数为DWORD类型，Bps）
	public static final int BRAC_USERSTATE_AUDIOBITRATE		=	10;	///< 用户当前的音频码率（参数为DWORD类型，Bps）
	public static final int BRAC_USERSTATE_P2PCONNECT		=	11;	///< 查询本地用户与指定用户的当前P2P连接状态（参数为DWORD类型，返回值：0 P2P不通， 1 P2P连接成功[TCP]，2 P2P连接成功[UDP]，3 P2P连接成功[TCP、UDP]）
	public static final int BRAC_USERSTATE_NETWORKSTATUS	=	12;	///< 查询指定用户的网络状态（参数为DWORD类型，返回值：0 优良，1 较好，2 一般，3 较差，4 非常差），注：查询间隔需要>1s
	public static final int BRAC_USERSTATE_VIDEOSIZE		=	13;	///< 查询指定用户的视频分辨率（参数为DWORD类型，返回值：低16位表示宽度，高16位表示高度）
	public static final int BRAC_USERSTATE_PACKLOSSRATE		=	14;	///< 查询指定用户的网络流媒体数据丢包率（参数为DWORD类型，返回值：0 - 100，如：返回值为5，表示丢包率为5%）
	public static final int BRAC_USERSTATE_DEVICETYPE		=	15;	///< 查询指定用户的终端类型（参数为DWORD类型，返回值：0 Unknow， 1 Windows，2 Android，3 iOS，4 Web，5 Linux，6 Mac，7 Win Phone，8 WinCE）
	public static final int BRAC_USERSTATE_SELFUSERSTATUS	=	16;	///< 查询本地用户的当前状态（参数为DWORD类型，返回值：0 Unknow，1 Connected，2 Logined，3 In Room，4 Logouted，5 Link Closed）
	public static final int BRAC_USERSTATE_SELFUSERID		=	17;	///< 查询本地用户的ID（参数为DWORD类型，若用户登录成功，返回用户实际的userid，否则返回-1）
	public static final int BRAC_USERSTATE_VIDEOROTATION	=	18;	///< 查询指定用户的当前视频旋转角度（参数为DWORD类型，返回角度值）
	public static final int BRAC_USERSTATE_VIDEOMIRRORED	=	19;	///< 查询指定用户的视频是否需要镜像翻转
	
	// 房间状态标志定义（API：BRAC_QueryRoomState 传入参数）
	public static final int BRAC_ROOMSTATE_ROOMNAME			=	1;	///< 房间名称（参数为字符串TCHAR类型）
	public static final int BRAC_ROOMSTATE_ONLINEUSERS		=	2;	///< 房间在线用户数（参数为DWORD型，不包含自己）
	
	// 视频呼叫事件类型定义（API：BRAC_VideoCallControl 传入参数、VideoCallEvent回调参数）
	public static final int BRAC_VIDEOCALL_EVENT_REQUEST	=	1;	///< 呼叫请求
	public static final int BRAC_VIDEOCALL_EVENT_REPLY		=	2;	///< 呼叫请求回复
	public static final int BRAC_VIDEOCALL_EVENT_START		=	3;	///< 视频呼叫会话开始事件
	public static final int BRAC_VIDEOCALL_EVENT_FINISH		=	4;	///< 挂断（结束）呼叫会话
	
	// 视频呼叫标志定义（API：BRAC_VideoCallControl 传入参数）
	public static final int BRAC_VIDEOCALL_FLAGS_AUDIO		= 0x01;	///< 语音通话
	public static final int BRAC_VIDEOCALL_FLAGS_VIDEO		= 0x02;	///< 视频通话
	public static final int BRAC_VIDEOCALL_FLAGS_FBSRCAUDIO	= 0x10;	///< 禁止源（呼叫端）音频
	public static final int BRAC_VIDEOCALL_FLAGS_FBSRCVIDEO	= 0x20;	///< 禁止源（呼叫端）视频
	public static final int BRAC_VIDEOCALL_FLAGS_FBTARAUDIO	= 0x40;	///< 禁止目标（被呼叫端）音频
	public static final int BRAC_VIDEOCALL_FLAGS_FBTARVIDEO	= 0x80;	///< 禁止目标（被呼叫端）视频
	
	// 视频方向修正标志定义
	public static final int BRAC_ROTATION_FLAGS_MIRRORED	= 0x1000;///< 图像需要镜像翻转
	public static final int BRAC_ROTATION_FLAGS_ROTATION90	= 0x2000;///< 顺时针旋转90度
	public static final int BRAC_ROTATION_FLAGS_ROTATION180	= 0x4000;///< 顺时针旋转180度
	public static final int BRAC_ROTATION_FLAGS_ROTATION270	= 0x8000;///< 顺时针旋转270度

	// 用户信息控制类型定义（API：BRAC_UserInfoControl 传入参数）
	public static final int BRAC_USERINFO_CTRLCODE_ROTATION	=	8;	///< 让指定的用户视频在显示时旋转，wParam为旋转角度参数
	public static final int BRAC_USERINFO_CTRLCODE_DEBUGLOG =   9;	///< 输出本地用户的调试日志，wParam为调试日志类型
	public static final int BRAC_USERINFO_CTRLCODE_LVORIENTFIX=	10;	///< 本地视频采集方向修正，wParam为方向参数，lParam为修正角度
	
	// 服务器信息查询常量定义（API：BRAC_QueryInfoFromServer 传入参数）
	public static final int ANYCHAT_SERVERQUERY_USERIDBYNAME =	1;	///< 根据用户昵称查询用户ID
	public static final int ANYCHAT_SERVERQUERY_USERIDBYSTRID=	2;	///< 根据字符串ID查询用户ID
	public static final int ANYCHAT_SERVERQUERY_STRIDBYUSERID=	3;	///< 根据用户ID查询字符串ID
	
	// SDK控制常量定义（API：BRAC_SDKControl 传入参数）
	public static final int ANYCHAT_SDKCTRL_BASE			=	1;	///< 基本功能控制
	public static final int ANYCHAT_SDKCTRL_OBJECT			=	20;	///< 对象操作
	public static final int ANYCHAT_SDKCTRL_VIDEOCALL		=	30;	///< 呼叫控制
	public static final int ANYCHAT_SDKCTRL_USERINFO		=	40;	///< 用户信息控制
	public static final int ANYCHAT_SDKCTRL_STREAMPLAY		=	50;	///< 流媒体播放
	public static final int ANYCHAT_SDKCTRL_NETWORK			=	60;	///< 网络控制
	public static final int ANYCHAT_SDKCTRL_MEDIA			=	70;	///< 媒体控制
	public static final int ANYCHAT_SDKCTRL_FILEDELETE		=	80;	///< 删除文件
	public static final int ANYCHAT_SDKCTRL_FILEINFO		=	81;	///< 获取文件信息
	public static final int ANYCHAT_SDKCTRL_DISKSIZE		=	82;	///< 获取磁盘容量
	public static final int ANYCHAT_SDKCTRL_FILEENCRYPT		=	83;	///< 文件加解密控制
	public static final int ANYCHAT_SDKCTRL_PPTHELPERINIT	=	90;	///< PPT环境初始化
	public static final int ANYCHAT_SDKCTRL_PPTFILECTRL		=	91;	///< PPT文件控制
	public static final int ANYCHAT_SDKCTRL_PPTFILEINFO		=	92;	///< PPT文件信息
	public static final int ANYCHAT_SDKCTRL_BUSINESS		=	95;	///< 业务控制
	
	// 常见出错代码定义
    public static final int BRAC_ERRORCODE_SUCCESS			= 0;	 ///< 没有错误
    public static final int BRAC_ERRORCODE_SESSION_QUIT		= 100101;///< 源用户主动放弃会话
    public static final int BRAC_ERRORCODE_SESSION_OFFLINE	= 100102;///< 目标用户不在线
    public static final int BRAC_ERRORCODE_SESSION_BUSY		= 100103;///< 目标用户忙
    public static final int BRAC_ERRORCODE_SESSION_REFUSE	= 100104;///< 目标用户拒绝会话
    public static final int BRAC_ERRORCODE_SESSION_TIMEOUT	= 100105;///< 会话请求超时
    public static final int BRAC_ERRORCODE_SESSION_DISCONNECT=100106;///< 网络断线
    
    
    // 录像功能标志定义（API：BRAC_StreamRecordCtrl 传入参数）
	public static final int ANYCHAT_RECORD_FLAGS_VIDEO		= 0x00000001;	///< 录制视频
	public static final int ANYCHAT_RECORD_FLAGS_AUDIO		= 0x00000002;	///< 录制音频
	public static final int ANYCHAT_RECORD_FLAGS_SERVER		= 0x00000004;	///< 服务器端录制
	public static final int ANYCHAT_RECORD_FLAGS_MIXAUDIO	= 0x00000010;	///< 录制音频时，将其它人的声音混音后录制
	public static final int ANYCHAT_RECORD_FLAGS_MIXVIDEO	= 0x00000020;	///< 录制视频时，将其它人的视频迭加后录制
	public static final int ANYCHAT_RECORD_FLAGS_ABREAST	= 0x00000100;	///< 录制视频时，将其它人的视频并列录制
	public static final int ANYCHAT_RECORD_FLAGS_STEREO		= 0x00000200;	///< 录制音频时，将其它人的声音混合为立体声后录制
	public static final int ANYCHAT_RECORD_FLAGS_SNAPSHOT	= 0x00000400;	///< 拍照
	public static final int ANYCHAT_RECORD_FLAGS_LOCALCB	= 0x00000800;	///< 触发本地回调
	public static final int ANYCHAT_RECORD_FLAGS_STREAM		= 0x00001000;	///< 对视频流进行录制（效率高，但可能存在视频方向旋转的问题）
	public static final int ANYCHAT_RECORD_FLAGS_USERFILENAME=0x00002000;	///< 用户自定义文件名
	public static final int ANYCHAT_RECORD_FLAGS_ERRORCODE	= 0x00004000;	///< 支持出错代码
	public static final int ANYCHAT_RECORD_FLAGS_MULTISTREAM= 0x00008000;	///< 支持多路流的录制（拍照）

	// 媒体播放事件类型定义
	public static final int ANYCHAT_STREAMPLAY_EVENT_START	=	3;	///< 播放开始事件
	public static final int ANYCHAT_STREAMPLAY_EVENT_FINISH	=	4;	///< 播放结束事件

	// 媒体播放标志定义（API：BRAC_StreamPlayInit 传入参数）
	public static final int ANYCHAT_STREAMPLAY_FLAGS_REPLACEAUDIOINPUT=	0x00000001;	///< 播放音频流代替本地音频输入（Mic）
	public static final int ANYCHAT_STREAMPLAY_FLAGS_REPLACEVIDEOINPUT=	0x00000002;	///< 播放视频流代替本地视频输入（Camera）
	public static final int ANYCHAT_STREAMPLAY_FLAGS_CALLBACKDATA	  =	0x00000010;	///< 回调数据给上层

	// 媒体播放信息类型定义（API：BRAC_StreamPlayGetInfo 传入参数）
	public static final int ANYCHAT_STREAMPLAY_INFO_JSONVALUE=	1;	///< 包含所有播放信息的Json字符串

	// 媒体播放控制类型定义（API：BRAC_StreamPlayControl 传入参数）
	public static final int ANYCHAT_STREAMPLAY_CTRL_START	=	1;	///< 开始播放
	public static final int ANYCHAT_STREAMPLAY_CTRL_PAUSE	=	2;	///< 暂停播放
	public static final int ANYCHAT_STREAMPLAY_CTRL_STOP	=	3;	///< 停止播放
	public static final int ANYCHAT_STREAMPLAY_CTRL_SEEK	=	4;	///< 位置拖动
	public static final int ANYCHAT_STREAMPLAY_CTRL_SPEEDCTRL=	5;	///< 速度调整
	public static final int ANYCHAT_STREAMPLAY_CTRL_OPENLOOP=	6;	///< 打开循环播放
	public static final int ANYCHAT_STREAMPLAY_CTRL_CLOSELOOP=	7;	///< 关闭循环播放

	// CoreSDK事件类型定义（回调函数：BRAC_CoreSDKEvent_CallBack参数）
	public static final int ANYCHAT_CORESDKEVENT_BASEEVENT	=	1;	///< SDK基础事件
	public static final int ANYCHAT_CORESDKEVENT_CAMERASTATE=	10;	///< 摄像头状态事件
	public static final int ANYCHAT_CORESDKEVENT_MICSTATE	=	11;	///< Mic状态事件
	public static final int ANYCHAT_CORESDKEVENT_TRANSFILE	=	12;	///< 文件传输事件
	public static final int ANYCHAT_CORESDKEVENT_STREAMPLAY	=	30;	///< 媒体播放事件
	public static final int ANYCHAT_CORESDKEVENT_BUSINESS	=	32;	///< 业务事件

	// CoreSDK回调数据类型定义（回调函数：BRAC_CoreSDKData_CallBack参数）
	public static final int ANYCHAT_CORESDKDATA_AUDIO		=	1;	///< 音频数据
	public static final int ANYCHAT_CORESDKDATA_VIDEO		=	2;	///< 视频数据
	public static final int ANYCHAT_CORESDKDATA_MESSAGE		=	3;	///< 文字数据
	public static final int ANYCHAT_CORESDKDATA_BUFFER		=	4;	///< 缓冲区数据
	
	
	
}