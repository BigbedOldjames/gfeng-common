package com.omysoft.common.enums;

/**
 * rest返回结果状态码
 * Created by xuyongjin on 2016-1-18.
 */
public enum ResultStatus {
    SUCCESS("200", "成功"),
    FAILED("500","失败"),
    AUTH_TOKEN_ERROR("401","TOKEN授权失败"),
    PASSWORD_CHANGE("403","密码变更"),
    UPLOAD_IMAGE_ERROR("501","图片上传失败");

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回结果描述
     */
    private String message;

    ResultStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
