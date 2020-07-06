package com.gckj.common.dto;

import com.gckj.common.entity.FileUpload;
import com.gckj.common.enums.ResultStatus;
import com.gckj.common.utils.OSSFileUploadUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
* 
* @Description：rest接口公用类
* @author：ldc
* date：2020-06-23
*/
public class BaseRest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Result Result(String code, String message, boolean flag) {
        return Result(code, message, flag, null);
    }

    public Result Result(String code, String message, boolean flag, Object object) {
        return Result(code, message, flag, object, null);
    }

    public Result Result(Object object, Object otherMap, String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(object);
        result.setOtherMap(otherMap);
        return result;
    }

    public Result Result(boolean result, String code, String message, Object data) {
        Result rs = new Result();
        rs.setResult(result);
        rs.setCode(code);
        rs.setMessage(message);
        rs.setData(data);
        return rs;
    }

    public Result Result(String code, String message, boolean flag, Object object, Object otherMap) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setResult(flag);
        result.setData(object);
        result.setOtherMap(otherMap);
        return result;
    }

    //----------------成功信息-----------------

    public Result SuccessResult() {
        return Result(ResultStatus.SUCCESS.getCode(), ResultStatus.SUCCESS.getMessage(), true);
    }

    public Result SuccessResult(Object object) {
        return Result(ResultStatus.SUCCESS.getCode(), ResultStatus.SUCCESS.getMessage(), true, object);
    }

    public Result SuccessResult(Object object, Long count) {
        return Result(ResultStatus.SUCCESS.getCode(), ResultStatus.SUCCESS.getMessage(), true, count, object);
    }

    public Result SuccessResult(String message, Object object) {
        return Result(ResultStatus.SUCCESS.getCode(), message, true, object);
    }

    public Result SuccessResult(String message) {
        return Result(ResultStatus.SUCCESS.getCode(), message, true, null);
    }

    public Result SuccessResult(Object object, Object otherMap) {
        return Result(object, otherMap, ResultStatus.SUCCESS.getCode(), ResultStatus.SUCCESS.getMessage());
    }

    public Result SuccessResult(Object object, Object otherMap, String message) {
        return Result(ResultStatus.SUCCESS.getCode(), message, true, object, otherMap);
    }

    /**
     * 设置成功信息
     */
    public Result SuccessMsgResult(String message) {
        return Result(ResultStatus.SUCCESS.getCode(), message, true);
    }

    //----------------错误信息-----------------

    public Result ErrorResult() {
        return Result(ResultStatus.FAILED.getCode(), ResultStatus.FAILED.getMessage(), false);
    }

    public Result ErrorResult(Object object) {
        return Result(ResultStatus.FAILED.getCode(), ResultStatus.FAILED.getMessage(), false, object);
    }

    public Result ErrorResult(Object object, Object otherMap, String message) {
        return Result(ResultStatus.FAILED.getCode(), message, false, object, otherMap);
    }

    public Result ErrorResult(boolean flag, String code,String message,Object object) {
        return Result(flag,code,message,object);
    }

    public Result ErrorResult(String message) {
        return Result(ResultStatus.FAILED.getCode(), message, false);
    }

    public Result ErrorResult(String message, Object object) {
        return Result(ResultStatus.FAILED.getCode(),message, false, object);
    }

    /**
     * 上传阿里云，返回url
     */
    public static String genMockMultipartFile(Map<String, Object> map) {
        String fileName = (String) map.get("fileName");// 文件名
        String fileType = (String) map.get("fileType");// 文件类型
        String content = (String) map.get("content");// 文件内容
        byte[] bytes = Base64.decodeBase64(content);
        String name = UUID.randomUUID().toString();
        MockMultipartFile file = new MockMultipartFile(name, fileName + "." + fileType, fileType, bytes);
        String urlPath = null;
        try {
            urlPath = OSSFileUploadUtils.uploadFile(file);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return urlPath;
    }

    public static String genMockMultipartFile(FileUpload fileUpload) {
        // 文件名
        String fileName = fileUpload.getFileName();
        // 文件类型
        String fileType = fileUpload.getFileType();
        // 文件内容
        String content = fileUpload.getContent();
        byte[] bytes = Base64.decodeBase64(content);
        String name = UUID.randomUUID().toString();
        MockMultipartFile file = new MockMultipartFile(name, fileName + "." + fileType, fileType, bytes);
        String urlPath = null;
        try {
            urlPath = OSSFileUploadUtils.uploadFile(file);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return urlPath;
    }
}
