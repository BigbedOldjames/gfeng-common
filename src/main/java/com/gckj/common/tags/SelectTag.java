package com.omysoft.common.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 弹出对话框
 * @author yy
 * @date 2016-1-13
 */
public class SelectTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected String name;
	protected String value;
	protected String labelName;
	protected String labelValue;
	protected String cssClass;
	protected String cssStyle;	
	protected String title;
	protected String url;
	protected String queryParams;

	


	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
