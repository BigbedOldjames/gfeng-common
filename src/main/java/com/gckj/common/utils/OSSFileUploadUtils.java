package com.omysoft.common.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 阿里云OSS工具类
 * 
 * @author yy
 * @date 2016-1-27
 */
public class OSSFileUploadUtils {

	//图片的
	private static String keyImgSuffixWithSlash = "img/";
	//文件的
	private static String keyFileSuffixWithSlash = "file/";

	public static String uploadFile(MultipartFile file) throws IOException {
		
		OSSClient client = OssUtils.getImgOSSClient();
		// 必须设置ContentLength
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentLength(file.getSize());
		String fileType = file.getOriginalFilename();
		fileType = fileType.substring(fileType.lastIndexOf("."));
		String key = "";
		if(fileType.equals("png")||fileType.equals("jpg")) {
			key = keyImgSuffixWithSlash + UUID.randomUUID().toString() + fileType;
		}else {
			key = keyFileSuffixWithSlash + UUID.randomUUID().toString() + fileType;
		}
		// 上传Object.
		PutObjectResult result = client.putObject(OssUtils.bucketName, key, file.getInputStream(), metaData);
		// 打印ETag
		// 文件在bucket里的唯一标识
		System.out.println(result.getETag());
		// 关闭client
		client.shutdown();
		// 文件资源路径
		String fileUrl = "http://" + OssUtils.bucketName + "." + OssUtils.imgEndpoint + "/" + key;
		return fileUrl;
	}
	
}
