package com.gckj.common.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @Description：freemarker工具类，单利模式创建Configuration对象，它处理创建和缓存预解析模板的工作
 * @author：ldc
 * date：2020-06-23
 */
public class FreemarkerUtils {
	
	private static Configuration defaultCfg = null;
	
	private FreemarkerUtils(){}
	
	/**
	 * 获取默认的Configuration
	 */
	public static Configuration getInstance() {  
	    if (defaultCfg == null) {  
	        synchronized (Configuration.class) {  
		        if (defaultCfg == null) {  
		        	defaultCfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		        	//设置模板文件的目录
		        	defaultCfg.setDefaultEncoding("UTF-8");
		        	defaultCfg.setClassForTemplateLoading(FreemarkerUtils.class, "/templates");
		        	//从多个位置加载模板
//		        	ClassTemplateLoader ctl = new ClassTemplateLoader(FreemarkerUtils.class, ""); 
//		        	TemplateLoader[] loaders = new TemplateLoader[] {ctl}; 
//		        	MultiTemplateLoader mtl = new MultiTemplateLoader(loaders); 
//		        	defaultCfg.setTemplateLoader(mtl);
		        }  
	        }  
	    }  
	    return defaultCfg;  
    }
	 
	public static Configuration buildConfiguration() {
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		cfg.setDefaultEncoding("UTF-8");
		return cfg;
	}
	 
	public static String processTemplateIntoString(String fileName, Object dataModel) {
		return processTemplateIntoString(getInstance(), fileName, dataModel);
	}
	
	public static String processTemplateIntoString(Configuration cfg, String fileName, Object dataModel) {
		 StringWriter writer = null;
		 try {
			Template template = cfg.getTemplate(fileName, new Locale("Zh_cn"), "utf-8");
			writer = new StringWriter();
			try {
				template.process(dataModel, writer);
				return writer.toString();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != writer){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
		return null;
	}
	 
	 /**
	 * 字符串作为freemarker模板
	 */
	public static String processStringTemplate(String stringTemplate, Object dataModel) {
		Configuration cfg = buildConfiguration();
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate("myTemplate", stringTemplate);
		cfg.setTemplateLoader(stringLoader);
		StringWriter writer = null;
		try {
			Template template = cfg.getTemplate("myTemplate", new Locale("Zh_cn"), "utf-8");
			writer = new StringWriter();
			try {
				template.process(dataModel, writer);
				return writer.toString();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null != writer){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
		return null;
	}
	
}
