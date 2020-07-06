package com.gckj.common.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.gckj.common.dto.Messager;

/**
* 
* @Description：控制器
* @author：ldc
* date：2020-06-23
*/

public class BaseController{
	
	/**
	 * @Description: 返回封装后的成功消息对象
	 */
	public Messager addMessager(Object obj){
		Messager messager = new Messager(true, Messager.TITLE_DEFAULT, null, obj);
		return messager;
	}
	
	/**
	 * @Description: 返回封装后的成功消息对象
	 */
	public Messager addMessager(Object obj, String message){
		Messager messager = new Messager(true, Messager.TITLE_DEFAULT, message, obj);
		return messager;
	}
	
	/**
	 * @Description: 返回封装后的消息对象
	 */
	public Messager addMessager(Object obj, String message, boolean success){
		Messager messager = new Messager(success, Messager.TTTLE_ERROR, message, obj);
		return messager;
	}
	
	/**
	 * @Description: 返回封装后的成功消息对象
	 */
	public Messager addSucMessager(String message){
		Messager messager = new Messager(true, message);
		return messager;
	}
	
	/**
	 * @Description: 返回封装后的失败消息对象
	 */
	public Messager addErrMessager(String message){
		Messager messager = new Messager(false, message);
		return messager;
	}
	

	public void writeText(HttpServletResponse response, String string) {
		response.setContentType("application/json; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(string);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeTextHtml(HttpServletResponse response, String string) {
		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(string);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
	}
}
