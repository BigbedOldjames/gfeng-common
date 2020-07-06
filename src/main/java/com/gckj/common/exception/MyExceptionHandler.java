package com.omysoft.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.omysoft.common.dto.Messager;

/**
 * spring 自定义异常拦截器
 * @author yy
 * @date 2016-1-18
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
	Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//String requestType = request.getHeader("X-Requested-With");
		logger.error(ex.getMessage());
		if (ex instanceof BusinessException) {
			Messager m = new Messager(false, ex.getMessage(), null);
			m.setSuccess(false);
			m.setMessage(ex.getMessage());
			response.setContentType("application/json; charset=UTF-8");
			try {
				PrintWriter out = response.getWriter();
				out.print(JSON.toJSONString(m));
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}
	
}