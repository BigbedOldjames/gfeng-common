package com.omysoft.common.utils;

import com.aliyun.oss.OSSClient;

/**
 * OSS管理工具类
 * @author JTG
 * @date 2020-3-24
 */
public class OssUtils {

	/**
	 * 阿里云Key
	 */
	private static String accessKeyId = "L1jh0dsdYYjiTnxZ";

	/**
	 * 阿里云密钥
	 */
	private static String accessKeySecret = "OlrIB1G4T0y3LjFXkrW0VR7gWZOnXq";

	/**
	 * 地址路径
	 */
	public static String imgEndpoint = "img-cn-hangzhou.aliyuncs.com";

	/**
	 * 地址路径
	 */
	public static String endpoint = "oss-cn-hangzhou.aliyuncs.com";

	/**
	 * 类似子域名
	 */
	public static String bucketName = "olmysoft";
	//---------------创建OSSClient实例, 可以有多个OSSClient，也可以并发使用-------
	/**
	 * 创建oss图片服务器的OSSClient
	 * 1、可使用对应图片处理服务
	 */
	public static OSSClient getImgOSSClient () {
		OSSClient client = new OSSClient(imgEndpoint, accessKeyId, accessKeySecret);
		return  client;
	}
	
	/**
	 * 创建oss服务器的OSSClient
	 */
	public static OSSClient getOSSClient () {
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		return  client;
	}
	
}
