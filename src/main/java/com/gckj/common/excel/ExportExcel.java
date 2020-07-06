/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.omysoft.common.excel;

import com.google.common.collect.Lists;
import com.omysoft.common.utils.DateUtils;
import com.omysoft.common.utils.Encodes;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
 * @author ThinkGem
 * @version 2013-04-21
 */
public class ExportExcel {
	
	private static Logger log = LoggerFactory.getLogger(ExportExcel.class);
			
	/**
	 * 工作薄对象
	 */
	private SXSSFWorkbook wb;
	
	/**
	 * 工作表对象
	 */
	private Sheet sheet;
	
	/**
	 * 样式列表
	 */
	private Map<String, CellStyle> styles;
	
	/**
	 * 当前行号
	 */
	private int rownum;
	
	/**
	 * 注解列表（Object[]{ ExcelField, Field/Method }）
	 */
	List<Object[]> annotationList = Lists.newArrayList();

	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headers 表头数组
	 */
	public ExportExcel(String title, String[] headers) {
		initialize(title, Lists.newArrayList(headers));
	}
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(String title, List<String> headerList) {
		initialize(title, headerList);
	}

	public ExportExcel(){}
	
	/**
	 * 初始化函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	private void initialize(String title, List<String> headerList) {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export");
		this.styles = createStyles(wb);
		// Create title
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
		}
		// Create header
		if (headerList == null){
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length==2){
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
						new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			}else{
				cell.setCellValue(headerList.get(i));
			}
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {  
			int colWidth = sheet.getColumnWidth(i)*2;
	        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
		log.debug("Initialize success.");
	}
	
	/**
	 * 创建表格样式
	 * @param wb 工作薄对象
	 * @return 样式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		return styles;
	}

	/**
	 * 添加一行
	 * @return 行对象
	 */
	public Row addRow(){
		return sheet.createRow(rownum++);
	}
	

	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val){
		return this.addCell(row, column, val, 0, Class.class);
	}
	
	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @param align 对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType){
		Cell cell = row.createCell(column);
		CellStyle style = styles.get("data"+(align>=1&&align<=3?align:""));
		try {
			if (val == null){
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				DataFormat format = wb.createDataFormat();
	            style.setDataFormat(format.getFormat("yyyy-MM-dd"));
				cell.setCellValue((Date) val);
			} else {
				if (fieldType != Class.class){
					cell.setCellValue((String)fieldType.getMethod("setValue", Object.class).invoke(null, val));
				}else{
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}
		} catch (Exception ex) {
			log.info("Set cell value ["+row.getRowNum()+","+column+"] error: " + ex.toString());
			cell.setCellValue(val.toString());
		}
		cell.setCellStyle(style);
		return cell;
	}

	
	/**
	 * 输出数据流
	 * @param os 输出数据流
	 */
	public ExportExcel write(OutputStream os) throws IOException{
		wb.write(os);
		return this;
	}
	
	/**
	 * 输出到客户端
	 * @param fileName 输出文件名
	 */
	public ExportExcel write(HttpServletResponse response, String fileName) throws IOException{
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
		write(response.getOutputStream());
		return this;
	}
	
	/**
	 * 输出到文件
	 * @param name 输出文件名
	 */
	public ExportExcel writeFile(String name) throws FileNotFoundException, IOException{
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		return this;
	}



	public <A,B> ExportExcel createExcel(HttpServletResponse response, String sheetName, Class<A> mainClzz, List<A> mainList, Class<B> subClzz, Map<String,String> mainFields, Map<String,String> subFields, String subListField) throws Exception {
		wb = new SXSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName);
		int rowIndex = 0;//表格行

		//创建主内容行
		boolean isCreatemainTitle = false;//没有子表时，主标题只创建一次
		for (A source:mainList) {
			if (!isCreatemainTitle) {
				//创建主标题行
				Row mainTitleRow = sheet.createRow(rowIndex);
				Iterator<String> mainTitleIter = mainFields.values().iterator();
				int mainTitleCell = 2;//主标题列
				while (mainTitleIter.hasNext()) {
					sheet.setColumnWidth(mainTitleCell, 20*256); //第一个参数代表列id(从0开始),第2个参数代表宽度值
					Cell cell = mainTitleRow.createCell(mainTitleCell);
					cell.setCellValue(mainTitleIter.next());
					// 创建单元格样式对象
					XSSFCellStyle ztStyle = (XSSFCellStyle) wb.createCellStyle();
					ztStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					// 创建字体对象
					Font ztFont = wb.createFont();
					ztFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体显示
					ztFont.setFontHeightInPoints((short)13); // 将字体大小设置为18px
					ztStyle.setFont(ztFont); // 将字体应用到样式上面
					cell.setCellStyle(ztStyle); // 样式应用到该单元格上
					mainTitleCell++;
				}
				if (subClzz == null) {
					isCreatemainTitle = true;
				}
				rowIndex++;
			}
			Row mainBodyRow = sheet.createRow(rowIndex);
			//创建主内容列
			Iterator<String> mainBodyIter = mainFields.keySet().iterator();
			Class clzz = mainClzz;
			int mainCellIdex = 2;
			while (mainBodyIter.hasNext()) {
				String field = mainBodyIter.next();
				String methodName = "get" + getMethodName(field);
				//分两种情况：1.如果主Field中有List
				if (!field.equals(subListField)) {
					Method method = clzz.getMethod(methodName);
					Object value = method.invoke(source);
					if (value != null) {
						if(value instanceof Date) {
							if(value.toString().contains("00:00:00")) {
								value = DateUtils.formatDate((Date) value,"yyyy-MM-dd");
							}else {
								value = DateUtils.formatDate((Date) value,"yyyy-MM-dd HH:mm:ss");
							}

						}
					}else {
						value = "";
					}

					String mainValue = value.toString();
					Cell boadyCell = mainBodyRow.createCell(mainCellIdex);
					boadyCell.setCellValue(mainValue);

					XSSFCellStyle ztStyle = (XSSFCellStyle) wb.createCellStyle();
					ztStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					boadyCell.setCellStyle(ztStyle); // 样式应用到该单元格上
					mainCellIdex++;
				}else {
					rowIndex++;
					//详细
					Method method = clzz.getMethod(methodName);
					List<B> dtlList = (List<B>) method.invoke(source);
					//List<WebSourceDtl> dtlList = source.getDtlList();
					//创建子标题行
					Row subTtileRow = sheet.createRow(rowIndex);///////////
					Iterator<String> subTitleIter = subFields.values().iterator();
					int subTitleCell= 2;
					while (subTitleIter.hasNext()) {
						Cell cell = subTtileRow.createCell(subTitleCell);
						cell.setCellValue(subTitleIter.next());
						// 创建单元格样式对象
						XSSFCellStyle ztStyle = (XSSFCellStyle) wb.createCellStyle();
						ztStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						// 创建字体对象
						Font ztFont = wb.createFont();
						ztFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体显示
						ztFont.setFontHeightInPoints((short)12); // 将字体大小设置为18px
						ztStyle.setFont(ztFont); // 将字体应用到样式上面
						cell.setCellStyle(ztStyle); // 样式应用到该单元格上
						subTitleCell++;
					}

					//创建子内容行
					for (B dtl:dtlList) {
						rowIndex++;
						Row subBodyRow = sheet.createRow(rowIndex);
						//创建子内容列
						Iterator<String> subBodyIter = subFields.keySet().iterator();
						//Class subClzz = WebSourceDtl.class;
						int subCellIdex = 2;
						while (subBodyIter.hasNext()) {
							String subMethodName = "get" + getMethodName(subBodyIter.next());
							Method subMethod = subClzz.getMethod(subMethodName);
							Object value = subMethod.invoke(dtl);
							if (value != null) {
								if(value instanceof Date) {
									value = DateUtils.formatDate((Date) value,"yyyy-MM-dd HH:mm:ss");
								}
							}else {
								value = "";
							}
							String subValue = value.toString();
							Cell subCell = subBodyRow.createCell(subCellIdex);
							subCell.setCellValue(subValue);
							XSSFCellStyle ztStyle = (XSSFCellStyle) wb.createCellStyle();
							ztStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
							subCell.setCellStyle(ztStyle); // 样式应用到该单元格上
							subCellIdex++;
						}
					}
				}

			}
			rowIndex++;
			Row blankRow = sheet.createRow(rowIndex);///////////
			blankRow.createCell(0).setCellValue("");
		}
		return write(response,"workbook.xls");
	}

	// 把一个字符串的第一个字母大写、效率是最高的、
	private static String getMethodName(String fildeName) throws Exception {
		fildeName = fildeName.contains("_") ? fildeName.replace("_", "").trim() : fildeName;
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
	
	/**
	 * 清理临时文件
	 */
	public ExportExcel dispose(){
		wb.dispose();
		return this;
	}
	
	/**
	 * 导出测试
	 */
//	public static void main(String[] args) throws Throwable {
//
//		List<String> headerList = Lists.newArrayList();
//		for (int i = 1; i <= 10; i++) {
//			headerList.add("表头"+i);
//		}
//
//		List<String> dataRowList = Lists.newArrayList();
//		for (int i = 1; i <= headerList.size(); i++) {
//			dataRowList.add("数据"+i);
//		}
//
//		List<List<String>> dataList = Lists.newArrayList();
//		for (int i = 1; i <=1000000; i++) {
//			dataList.add(dataRowList);
//		}
//
//		ExportExcel ee = new ExportExcel("表格标题", headerList);
//
//		for (int i = 0; i < dataList.size(); i++) {
//			Row row = ee.addRow();
//			for (int j = 0; j < dataList.get(i).size(); j++) {
//				ee.addCell(row, j, dataList.get(i).get(j));
//			}
//		}
//
//		ee.writeFile("D:/export.xlsx");
//
//		ee.dispose();
//
//		log.debug("Export success.");
//
//	}

}
