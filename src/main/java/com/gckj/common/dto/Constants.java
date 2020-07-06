package com.gckj.common.dto;

public class Constants {

	/**
	 * 构造函数
	 */
	private Constants() {
	}

	//---------------------------------------------公共------------------------------------------
	/**
	 * 操作成功
	 */
	public final static int SUCCESS =200;
	
	/**
	 * 操作失败
	 */
	public final static int FAILED = 500;
	
	/**
	 * 数据为空
	 */
	public final static int DATA_IS_NULL = -2;
	
	/**
	 * 未知错误
	 */
	public final static int UNKNOWN = 400;
	

	//----------------------------------------------会员------------------------------------------
	/**
	 * 企业会员
	 */
	public final static int WEB_MEMBER_ENTERPRISE = 0;
	/**
	 * 个人会员
	 */
	public final static int WEB_MEMBER_PERSON = 1;
	
	/**
	 * 会员账号已存在
	 */
	public final static int WEB_MEMBER_LOGINNAME_EXIST = 2;
	/**
	 * 会员手机已注册
	 */
	public final static int WEB_MEMBER_TEL_EXIST = 3;
	
	/**
	 *会员已加盟 
	 **/
	public final static int WEB_JOIN_APPLY_EXIST= 1;

	/**
	 * 存储当前登录用户id的字段名
	 */
	public static final String CURRENT_USER_ID = "CURRENT_USER_ID";


	/**
	 * 存放Authorization的header字段
	 */
	public static final String AUTHORIZATION = "authorization";

	/**
	 * 生成运单Excel内部路径
	 */
	public static final String REDIS_LOADING_LIST_INSIDE_MAIL_PATH = "RedisLoadingListInsideMailPath";

	/**
	 * 生成运单Excel外部路径
	 */
	public static final String REDIS_LOADING_LIST_EXTERNAL_MAIL_PATH = "RedisLoadingListExternalMailPath";

	public static final String REDIS_LOADING_LIST_IDS = "RedisLoadingListIds";
	
	public static final String REDIS_REGISTER_TEL = "registerTel_"; // redis中注册验证码

	public static final String REDIS_SMS_IP_VALID_DATE = "smsIpValidDate_"; // redis中注册验证码

	public static final String REDIS_SMS_SEND_COUNT_TEL = "smsSendCountTel_"; // redis短信发送次数
	
	public static final String REDIS_LOGIN_TEL = "loginTel_"; // redis中登录字符串

	public static final String CACHE_REST_BUSINESS_LOADINGLIST_DATA = "businessLoadingListData_"; // 业务数据导入-运单
	
	public static final String REDIS_VERIFICATION_CODE = "VCode_"; // redis中登录字符串
	
	public static final String REDIS_FASTLOGIN_TEL = "fastLoginTel_"; // redis中验证码登录
	
	public static final int REDIS_SECONDS= 3600 * 24;	//redis默认生存时间

	public static final int REDIS_SMS_SECONDS= 60;	//redis默认生存时间
	
	public static final int REDIS_VCODE_SECONDS= 30000;	//验证码有效时间
	
	public static final String USER_SALT = "6f5b8e72-911b-4d6d-b4e4-c7a98c3bf7ab"; // 用户盐

	public static final int CKHR_TOKEN_SECONDS = 25920000;

	public static final String CKHR_TOKEN = "CKHRTOKEN";

	/*Hash缓存*/
	public static final String CACHE =  "cache:";

	/**
	 * 用户权限数据缓存
	 */
	public static final String CACHE_REST_DATA_SCOPE =  CACHE + "restDataScope";

	/**
	 * 用户自定义配置项缓存
	 */
	public static final String CACHE_REST_CONFIG =  CACHE + "restConfig";

	/**
	 * 枚举字典缓存
	 */
	public static final String CACHE_DICTIONARY_LIST_CACHE =  CACHE + "dictionaryList";

	/**
	 * 用户权限菜单数据
	 */
	public static final String CACHE_REST_FUNCTION_DATA_SCOPE =  CACHE + "functionDataScope";

	/**
	 * 行政区域地址
	 */
	public static final String CACHE_REST_REGION_JSON_DATA_SCOPE =  CACHE + "regionJSON";
}
