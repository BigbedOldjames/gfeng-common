package com.omysoft.common.utils;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * RESTFUL接口客户端工具类
 * @author mjy
 *
 */
public class RestClientUtils {

	
	/**
	 * 发送查询请求给后台
	 * @param url 后台url，Controller中配置的地址，RequestMapping加方法名，不含http以及项目名称
	 * @param obj 作为查询条件发送给后台的对象
	 * @param clazz 返回对象类型
	 * @return
	 * 调用示例：Object ret = RestClientUtil.sendQueryRequest(request, "/rest/member/23", member, WebMember.class);
	 */
	static RestTemplate restTemplate = (RestTemplate) ApplicationContextUtils.getBean("restTemplate");

	public static Object sendQueryRequest(String requestUrl, Object obj, Class<?> clazz) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		String jsonObj = JSON.toJSONString(obj);
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj, headers);
		try {
			Object ret = restTemplate.postForObject(requestUrl, formEntity, clazz);
			if (ret != null)
				return ret;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送查询请求给后台--重载
	 * @param obj
     * @return jsonNode
     */
	public static JsonNode sendQueryRequest(String requestUrl,Object obj) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		String jsonObj = JSON.toJSONString(obj);
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj, headers);
		try {
			JsonNode ret = restTemplate.postForObject(requestUrl, formEntity, JsonNode.class);
			if (ret != null)
				return ret;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static String sendRequestT(String requestUrl,Object obj) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		String jsonObj = JSON.toJSONString(obj);
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj, headers);
		String result = restTemplate.postForObject(requestUrl, formEntity, String.class);
		if (result == null)
			return null;
		return result;
	}

	/**
	 * 返回单个实体类
	 * @param obj
	 * @param clz
	 * @param <T>
     * @return
     */
	public static <T> T sendRequestT(String requestUrl , Object obj, Class<T> clz) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		String jsonObj = JSON.toJSONString(obj);
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj, headers);
		String result = restTemplate.postForObject(requestUrl, formEntity, String.class);
		if (result == null) return null;
		return JSON.parseObject(result,clz);
	}
	
	
	public static String sendRequestT(String host,String path, Object obj) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType
				.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		headers.add("accessType","1");
		String jsonObj = JsonMapper.toJsonString(obj);

		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj, headers);

		String url = host + path;
		String result = restTemplate.postForObject(url, formEntity,
				String.class);
		if (result == null)
			return null;
		return result;
	}

	public static <T>T getForObject(String url, Class<T> class1, Map<String, Object> urlVariables) {
		return restTemplate.getForObject(url, class1, urlVariables);
	}
	
	public static String getForObject(String url, Map<String, Object> urlVariables) {
		try {
			return restTemplate.getForObject(url, String.class, urlVariables);
		} catch (RestClientException e) {
			return "{\"errcode\":-100,\"errmsg\":" + e.getMessage() +"}";
		}
	}
	
}
