package com.gckj.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Description：结果集对象，返回对象
 * @author：ldc
 * date：2020-06-23
 */
public class Result implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2796916901317766775L;
    private String message; //消息提示信息
    private String code;    //消息返回编码
    private boolean result; //操作是否成功
    private String token;
    private Long count;
    private Object data;    //主数据对象
    private Object otherMap; //其他附加返回值
    private String dataScope;

    public Result() {
    }

    public Result(String code, String message, boolean result, Object data) {
        this.message = message;
        this.code = code;
        this.result = result;
        this.data = data;
    }

    public Result(String code, String message, boolean result) {
        this.message = message;
        this.code = code;
        this.result = result;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getOtherMap() {
        return otherMap;
    }

    public void setOtherMap(Object otherMap) {
        this.otherMap = otherMap;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

}
