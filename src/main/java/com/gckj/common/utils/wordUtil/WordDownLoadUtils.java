package com.gckj.common.utils.wordUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WordDownLoadUtils {

	private static Log log = LogFactory.getLog(WordDownLoadUtils.class);

	public static void downLoadUrl(HttpServletRequest req, HttpServletResponse resp, String html, String templatePath, String fileName) {
		// 用map存放数据
		HashMap<String, Object> param = new HashMap<String, Object>();
		RichObject richObject = new RichObject();
		richObject.setHtml(html);
		File file = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		// 从mht文件中找
		richObject.setDocSrcLocationPrex("file:///C:/268D4AA4");
		richObject.setDocSrcParent("word.files");
		richObject.setNextPartId("01D2C8DD.BC13AF60");
		richObject.setShapeidPrex("_x56fe__x7247__x0020");
		richObject.setTypeid("#_x0000_t75");
		richObject.setSpidPrex("_x0000_i");

		richObject.setWebAppliction(false);
		RichHtmlHandler richHtmlHandler = null;
		//D:\zoutao\eclipse\eclipse\workspace\workspaceotms\
		String docFilePath = templatePath + fileName+".doc";
		try {
			richHtmlHandler = WordGeneratorWithFreemarker.createRichHtmlHandler(richObject);
	        List<RichHtmlHandler> richHtmlHandlerList = new ArrayList<RichHtmlHandler>();
	        richHtmlHandlerList.add(richHtmlHandler);
	        param.put("imagesXmlHrefString", WordGeneratorWithFreemarker.getXmlImgHref(richHtmlHandlerList));
	        log.debug("------imagesXmlHrefString-------"+WordGeneratorWithFreemarker.getXmlImgHref(richHtmlHandlerList));
	        param.put("imagesBase64String", WordGeneratorWithFreemarker.getImagesBase64String(richHtmlHandlerList));
	        log.debug("------imagesBase64String-------"+WordGeneratorWithFreemarker.getImagesBase64String(richHtmlHandlerList));
	        param.put("context", richHtmlHandler.getHandledDocBodyBlock());
			templatePath = java.net.URLDecoder.decode(templatePath, "utf-8");
			// 这里我的路径有空格添加此处理
			log.debug("------templatePath-------" + templatePath);
			file = WordGeneratorWithFreemarker.createDoc(templatePath, "contactWord.ftl", param, docFilePath);
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("application/x-msdownload;");
			resp.setHeader("Content-disposition", "attachment; filename=" + fileName + ".doc");
			resp.setHeader("Content-Length", String.valueOf(file.length()));
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(resp.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
				bos.write(buff, 0, bytesRead);
			System.out.println("success");
			bos.flush();
		} catch (Exception e) {
			System.out.println("导出文件失败！");
		} finally {
			try {
				if (bis != null) 
					bis.close();
				if (bos != null) 
					bos.close();
				file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
