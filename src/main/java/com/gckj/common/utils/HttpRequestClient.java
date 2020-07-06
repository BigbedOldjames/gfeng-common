package com.gckj.common.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

import java.util.Map;

/**
 * @Description：http请求客户端
 * @author：ldc
 * date：2020-06-23
 */
public class HttpRequestClient {

    public static String PostHttpRequest(String url, String body,Map<String, String> headerMap) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        HttpRequest post = HttpRequest.post(url);
        if(StringUtils.isNotNull(headerMap)){
            return post.addHeaders(headerMap).body(body).execute().body();
        }
        if(StringUtils.isNotBlank(body)){

            return post.body(body).execute().body();
        }
        return post.execute().body();
    }

    public static String doGet(String url, Map<String,Object> params) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        return HttpUtil.get(url, params);
    }

    public static String doPostForm(String url, String name, Object obj) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        HttpRequest post = HttpRequest.post(url);
        return post.form(name, obj).execute().body();
    }

}
