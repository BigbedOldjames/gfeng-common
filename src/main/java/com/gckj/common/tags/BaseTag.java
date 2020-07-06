package com.omysoft.common.tags;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.omysoft.common.utils.CookieUtils;


/**
 * 自定义标签动态生成CSS、JS
 * @author yy
 * @date 2015-12-11
 */
public class BaseTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String type = "default";// 加载类型

	public void setType(String type) {
		this.type = type;
	}

	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		try {
			//设置默认皮肤
			String theme = CookieUtils.getCookie((HttpServletRequest) this.pageContext.getRequest(), "easyuiTheme");
			if (theme == null || "".equals(theme.trim())) {
				theme = "default";
			}
			JspWriter out = this.pageContext.getOut();
			StringBuffer css = new StringBuffer();
			StringBuffer js = new StringBuffer();

			List<String> list = Arrays.asList(type.split(","));
			if (list.contains("jquery")) { //根据不同浏览器选择jquery版本 IE 6/7/8选择 1.9.1版
				js.append("<!--[if lt IE 9]>");
				js.append("<script type=\"text/javascript\" src=\"plugins/jquery/jquery-1.9.1.min.js\"></script>");
				js.append("<![endif]-->");
				js.append("<!--[if gte IE 9]><!-->");
				js.append("<script type=\"text/javascript\" src=\"plugins/jquery/jquery-2.0.3.min.js\"></script>");
				js.append("<![endif]-->");
			}
			if (list.contains("easyui")) { //引入jquery easyui
				css.append("<link id=\"defaultEasyuiTheme\" rel=\"stylesheet\" type=\"text/css\" href=\"plugins/jquery-easyui-1.4.4/themes/" + theme + "/easyui.css\">");
				js.append("<script type=\"text/javascript\" src=\"plugins/jquery-easyui-1.4.4/jquery.easyui.min.js\"></script>");
				js.append("<script type=\"text/javascript\" src=\"plugins/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js\"></script>");
			}
			if (list.contains("ztree")) { //引入jquery ztree
				css.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plugins/zTree/css/zTreeStyle/zTreeStyle.css\">");
				js.append("<script type=\"text/javascript\" src=\"plugins/zTree/js/jquery.ztree.all-3.5.js\"></script>");
			}
			if (list.contains("plupload")) { //引入plupload
				js.append("<script type=\"text/javascript\" src=\"plugins/plupload-2.1.2/js/plupload.full.min.js\"></script>");
				js.append("<script type=\"text/javascript\" src=\"plugins/jslib/plupload-2.1.2/js/i18n/zh_CN.js\"></script>");
			}
			if (list.contains("autocomplete")) { //引入autocomplete
				css.append("<link rel=\"stylesheet\" href=\"plugins/autocomplete/jquery.autocomplete.css\" type=\"text/css\">");
				js.append("<script type=\"text/javascript\" src=\"plugins/autocomplete/jquery.autocomplete.js\"></script>");
			}
			if (list.contains("myPlugins")) {	//引入自定义工具
				js.append("<script type=\"text/javascript\" src=\"plugins/jquery/jquery.cookie.js\"></script>");
				js.append("<script type=\"text/javascript\" src=\"plugins/myPlugins/js/json2.js\"></script>");
				js.append("<script type=\"text/javascript\" src=\"plugins/myPlugins/js/jqueryUtil.js\"></script>");
			}
			//自定义CSS
			css.append("<link rel=\"stylesheet\" href=\"plugins/myPlugins/css/icon.css\" type=\"text/css\">");
			css.append("<link rel=\"stylesheet\" href=\"plugins/myPlugins/css/common.css\" type=\"text/css\">");
			out.print(css.toString() + js.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
