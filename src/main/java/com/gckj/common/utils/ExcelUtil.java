package com.gckj.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gckj.common.utils.StringUtils;

public class ExcelUtil {
	public static final String HEADERINFO = "headInfo";
	public static final String HEADERDTLINFO = "headDtlInfo";
	public static final String DATAINFON = "dataInfo";

	/**
	 * 
	 * @Title: getWeebWork
	 * @Description: TODO(根据传入的文件名获取工作簿对象(Workbook))
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWeebWork(String filename) throws IOException {
		Workbook workbook = null;
		if (null != filename) {
			String fileType = filename.substring(filename.lastIndexOf("."), filename.length());
			FileInputStream fileStream = new FileInputStream(new File(filename));
			if (".xls".equals(fileType.trim().toLowerCase())) {
				workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
			} else if (".xlsx".equals(fileType.trim().toLowerCase())) {
				workbook = new XSSFWorkbook(fileStream);// 创建 Excel 2007 工作簿对象
			}
		}
		return workbook;
	}

	/**
	 * 
	 * @Title: writeExcel (不带文件缓存)
	 * @Description: TODO(导出Excel表)
	 * @param pathname
	 *            :导出Excel表的文件路径
	 * @param map
	 *            ：封装需要导出的数据(HEADERINFO封装表头信息，DATAINFON：封装要导出的数据信息,此处需要使用TreeMap
	 *            ) 例如： map.put(ExcelUtil.HEADERINFO,List<String> headList);
	 *            map.put(ExcelUtil.DATAINFON,List<TreeMap<String,Object>>
	 *            dataList);
	 * @param wb
	 * @throws IOException
	 */
	public static Workbook writeExcelFile(String fileName, Map<String, Object> map, Workbook wb,
			HttpServletResponse response) throws IOException {
		if (null != map) {
			List<Object> headList = (List<Object>) map.get(ExcelUtil.HEADERINFO);
			List<TreeMap<String, Object>> dataList = (List<TreeMap<String, Object>>) map.get(ExcelUtil.DATAINFON);
			CellStyle style = getCellStyle(wb);
			Sheet sheet = wb.createSheet();
			/**
			 * 设置Excel表的第一行即表头
			 */
			Row row = sheet.createRow(0);
			for (int i = 0; i < headList.size(); i++) {
				Cell headCell = row.createCell(i);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);// 设置表头样式
				headCell.setCellValue(String.valueOf(headList.get(i)));
			}

			for (int i = 0; i < dataList.size(); i++) {
				Row rowdata = sheet.createRow(i + 1);// 创建数据行
				TreeMap<String, Object> mapdata = dataList.get(i);
				Iterator it = mapdata.keySet().iterator();
				int j = 0;
				while (it.hasNext()) {
					String strdata = String.valueOf(mapdata.get(it.next()));
					Cell celldata = rowdata.createCell(j);
					celldata.setCellType(Cell.CELL_TYPE_STRING);
					celldata.setCellValue(strdata);
					j++;
				}
			}
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
			OutputStream out = response.getOutputStream();
			wb.write(out);
		}
		return wb;
	}

	/**
	 * 
	 * @Title: writeExcel
	 * @Description: TODO(导出Excel表)
	 * @param pathname
	 *            :导出Excel表的文件路径
	 * @param map
	 *            ：封装需要导出的数据(HEADERINFO封装表头信息，DATAINFON：封装要导出的数据信息,此处需要使用TreeMap
	 *            ) 例如： map.put(ExcelUtil.HEADERINFO,List<String> headList);
	 *            map.put(ExcelUtil.DATAINFON,List<TreeMap<String,Object>>
	 *            dataList);
	 * @param wb
	 * @throws IOException
	 */
	public static void writeExcel(String pathname, Map<String, Object> map, Workbook wb, HttpServletResponse response)
			throws IOException {
		if (null != map) {
			List<Object> headList = (List<Object>) map.get(ExcelUtil.HEADERINFO);
			List<TreeMap<String, Object>> dataList = (List<TreeMap<String, Object>>) map.get(ExcelUtil.DATAINFON);
			CellStyle style = getCellStyle(wb);
			Sheet sheet = wb.createSheet();
			/**
			 * 设置Excel表的第一行即表头
			 */
			Row row = sheet.createRow(0);
			for (int i = 0; i < headList.size(); i++) {
				Cell headCell = row.createCell(i);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);// 设置表头样式
				headCell.setCellValue(String.valueOf(headList.get(i)));
			}

			for (int i = 0; i < dataList.size(); i++) {
				Row rowdata = sheet.createRow(i + 1);// 创建数据行
				TreeMap<String, Object> mapdata = dataList.get(i);
				Iterator it = mapdata.keySet().iterator();
				int j = 0;
				while (it.hasNext()) {
					String strdata = String.valueOf(mapdata.get(it.next()));
					Cell celldata = rowdata.createCell(j);
					celldata.setCellType(Cell.CELL_TYPE_STRING);
					celldata.setCellValue(strdata);
					j++;
				}
			}
			File file = new File(pathname);
			OutputStream os = new FileOutputStream(file);
			os.flush();
			wb.write(os);
			os.close();

			File f = new File(pathname);
			if (!f.exists()) {
				response.sendError(404, "File not found!");
				return;
			}
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
			OutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.close();
		}
	}

	/**
	 * 
	 * @Title: writeExcel
	 * @Description: TODO(导出Excel表带子表明细)
	 * @param pathname
	 *            :导出Excel表的文件路径
	 * @param map
	 *            ：封装需要导出的数据(HEADERINFO封装表头信息，DATAINFON：封装要导出的数据信息,此处需要使用TreeMap
	 *            ) 例如： map.put(ExcelUtil.HEADERINFO,List<String> headList);
	 *            map.put(ExcelUtil.DATAINFON,List<TreeMap<String,Object>>
	 *            dataList);
	 * @param wb
	 * @throws IOException
	 */
	public static void writeExcelS(String fileName, Map<String, Object> map, Workbook wb, HttpServletResponse response)
			throws IOException {
		if (null != map) {
			List<Object> headList = (List<Object>) map.get(ExcelUtil.HEADERINFO);
			List<Object> headDtlList = (List<Object>) map.get(ExcelUtil.HEADERDTLINFO);
			List<LinkedHashMap<String, Object>> dataList = (List<LinkedHashMap<String, Object>>) map
					.get(ExcelUtil.DATAINFON);
			CellStyle style = getCellStyle(wb);
			Sheet sheet = wb.createSheet();
			sheet.setColumnWidth(0, 20 * 300);
			sheet.setColumnWidth(1, 20 * 300);
			sheet.setColumnWidth(2, 20 * 400);
			/**
			 * 设置Excel表头
			 */
			Row row = sheet.createRow(0);
			for (int k = 0; k < headList.size(); k++) {
				Cell headCell = row.createCell(k);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);// 设置表头样式
				headCell.setCellValue(String.valueOf(headList.get(k)));
			}
			for (int i = 0; i < headDtlList.size(); i++) {
				Cell headCell = row.createCell(headList.size() + i);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);// 设置表头样式
				headCell.setCellValue(String.valueOf(headDtlList.get(i)));
			}
			int index = 0;
			for (int i = 0; i < dataList.size(); i++) {
				/*
				 * if(i!=0){ index = index+1; }
				 */
				LinkedHashMap<String, Object> mapdata = dataList.get(i);
				List<LinkedHashMap<String, Object>> dtldata = (List) mapdata.get("dtlData");
				if (dtldata != null && dtldata.size() > 0) {
					mapdata.remove("dtlData");
				}
				Iterator it = mapdata.keySet().iterator();
				if (dtldata != null && dtldata.size() > 0) {
					for (int k = 0; k < dtldata.size(); k++) {
						int j = 0;
						LinkedHashMap<String, Object> mapdtlData = dtldata.get(k);
						index = index + 1;
						Row rowdata = sheet.createRow(index);// 创建数据行
						Iterator itDtl = mapdtlData.keySet().iterator();
						Cell celldata = null;
						if (k == 0) {
							while (it.hasNext()) {
								String strdata = String.valueOf(mapdata.get(it.next()));
								if (strdata.equals("null")) {
									strdata = "";
								}
								celldata = rowdata.createCell(j);
								celldata.setCellType(Cell.CELL_TYPE_STRING);
								celldata.setCellValue(strdata);
								j = ++j;
							}
						} else {
							j = j + mapdata.size();
						}
						while (itDtl.hasNext()) {
							j++;
							String strdata = String.valueOf(mapdtlData.get(itDtl.next()));
							if (strdata.equals("null")) {
								strdata = "";
							}
							celldata = rowdata.createCell(j - 1);
							celldata.setCellType(Cell.CELL_TYPE_STRING);
							celldata.setCellValue(strdata);
						}
					}
				}
			}
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
			OutputStream out = response.getOutputStream();
			wb.write(out);
		}
	}

	/**
	 * 
	 * @Title: getCellStyle
	 * @Description: TODO（设置表头样式）
	 * @param wb
	 * @return
	 */
	public static CellStyle getCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		style.setFillForegroundColor(HSSFColor.LIME.index);// 设置背景色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);// 让单元格居中
		// style.setWrapText(true);//设置自动换行
		style.setFont(font);
		return style;
	}

	/**
	 * 
	 * @Title: readerExcelDemo
	 * @Description: TODO(读取Excel表中的数据)
	 * @throws IOException
	 */
	public static void readerExcelDemo() throws IOException {
		/**
		 * 读取Excel表中的所有数据
		 */
		Workbook workbook = getWeebWork("E:/结算单503411妥投结算数据.xlsx");
		System.out.println("总表页数为：" + workbook.getNumberOfSheets());// 获取表页数
		Sheet sheet = workbook.getSheetAt(0);
		int rownum = sheet.getLastRowNum();// 获取总行数
		for (int i = 0; i < rownum; i++) {
			Row row = sheet.getRow(i);
			Cell orderno = row.getCell(2);// 获取指定单元格中的数据
			System.out.println(orderno.getCellType());
			short cellnum = row.getLastCellNum(); // 获取单元格的总列数
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				Cell celldata = row.getCell(j);
				System.out.print(celldata + "\t");
			}
			System.out.println();
		}

		/**
		 * 读取指定位置的单元格
		 */
		Row row1 = sheet.getRow(1);
		Cell cell1 = row1.getCell(2);
		System.out.print("(1,2)位置单元格的值为：" + cell1);
		BigDecimal big = new BigDecimal(cell1.getNumericCellValue());// 将科学计数法表示的数据转化为String类型
		System.out.print("\t" + String.valueOf(big));

	}

	public static void writeExcelInfo(String fileName, Map<String, Object> map, Workbook wb,
			HttpServletResponse response) throws IOException {
		if (null != map) {
			List<Object> headList = (List<Object>) map.get(ExcelUtil.HEADERINFO);
			List<LinkedHashMap<String, Object>> dataList = (List<LinkedHashMap<String, Object>>) map
					.get(ExcelUtil.DATAINFON);
			List<String> numberList = new ArrayList<String>();
			if(map.get("numberList")!=null){
				numberList = (List<String>) map.get("numberList");
			}
			CellStyle style = getCellStyle(wb);
			Sheet sheet = wb.createSheet();
			sheet.setColumnWidth(0, 20 * 300);
			sheet.setColumnWidth(1, 20 * 300);
			sheet.setColumnWidth(2, 20 * 200);
			sheet.setColumnWidth(3, 20 * 200);
			sheet.setColumnWidth(4, 20 * 300);
			if(StringUtils.isNotNull(map.get("columnHidden"))){
				sheet.setColumnHidden(Integer.parseInt(map.get("columnHidden").toString()), true);
			}
			if(StringUtils.isNotNull(map.get("columnHidden2"))){
				sheet.setColumnHidden(Integer.parseInt(map.get("columnHidden2").toString()), true);
			}
			/**
			 * 设置Excel表头
			 */
			Row row = sheet.createRow(0);
			row.setHeight((short) 600);
			Cell titleCell = row.createCell(0);
			String title = "清单";
			if (StringUtils.isNotNull(map.get("title"))) {
				title = map.get("title").toString();
			}
			titleCell.setCellValue(title);
			CellStyle headStyle = wb.createCellStyle();
			headStyle.setAlignment(CellStyle.ALIGN_CENTER);// 居中
			headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中

			Font font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			font.setFontHeight((short) 400);
			headStyle.setFont(font);
			titleCell.setCellStyle(headStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));

			row = sheet.createRow(1);
			if (StringUtils.isNotNull(map.get("content"))) {
				Cell infoCell = row.createCell(0);
				infoCell.setCellValue(map.get("content").toString());
			}
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 12));

			row = sheet.createRow(2);
			for (int k = 0; k < headList.size(); k++) {
				Cell headCell = row.createCell(k);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);// 设置表头样式
				headCell.setCellValue(String.valueOf(headList.get(k)));
			}
			int index = 2;
			for (int i = 0; i < dataList.size(); i++) {
				/*
				 * if(i!=0){ index = index+1; }
				 */
				LinkedHashMap<String, Object> mapdata = dataList.get(i);
				Iterator it = mapdata.keySet().iterator();
				int j = 0;
				index = index + 1;
				Row rowdata = sheet.createRow(index);// 创建数据行
				Cell celldata = null;
				
				while (it.hasNext()) {
					String head = (String) it.next();
					String strdata = String.valueOf(mapdata.get(head));
					if (strdata.equals("null")) {
						strdata = "";
					}
					celldata = rowdata.createCell(j);
					/*if(numberList.contains(head)){
						celldata.setCellStyle(cellStyle);
						if(StringUtils.isNotNull(strdata)){
							celldata.setCellValue(Double.parseDouble(strdata));
						}
					}else{
						celldata.setCellType(Cell.CELL_TYPE_STRING);
						celldata.setCellValue(strdata);
					}*/
					if(numberList.contains(head)){
						Boolean isNum = false;//data是否为数值型
		                Boolean isInteger=false;//data是否为整数
		                Boolean isPercent=false;//data是否为百分数
		                if (StringUtils.isNotNull(strdata)) {
		                    //判断data是否为数值型
		                    isNum = strdata.matches("^(-?\\d+)(\\.\\d+)?$");
		                    //判断data是否为整数（小数部分是否为0）
		                    isInteger=strdata.matches("^[-\\+]?[\\d]*$");
		                    //判断data是否为百分数（是否包含“%”）
		                    isPercent=strdata.contains("%");
		                }

		                //如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
		                CellStyle cellStyle = wb.createCellStyle();  
		                if (isNum && !isPercent) {
		                    DataFormat df = wb.createDataFormat(); // 此处设置数据格式
		                    if (isInteger) {
		                    	cellStyle.setDataFormat(df.getFormat("#,#0"));//数据格式只显示整数
		                    }else{
		                    	cellStyle.setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点
		                    }           
		                    // 设置单元格格式
		                    celldata.setCellStyle(cellStyle);
		                    // 设置单元格内容为double类型
		                    celldata.setCellValue(Double.parseDouble(strdata));
		                } else {
		                	celldata.setCellType(Cell.CELL_TYPE_STRING);
		                    // 设置单元格内容为字符型
		                	celldata.setCellValue(strdata);
		                }
					}else{
						celldata.setCellType(Cell.CELL_TYPE_STRING);
						celldata.setCellValue(strdata);
					}
					
					
					j = ++j;
					
				}
			}

			if (StringUtils.isNotNull(map.get("lastMap"))) {
				index = index + 1;
				row = sheet.createRow(index);
				Map<Integer, String> lastMap = (Map<Integer, String>) map.get("lastMap");
				for (Integer key : lastMap.keySet()) {
					Cell infoCell = row.createCell(key);
					infoCell.setCellValue(lastMap.get(key));
				}
			}
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
			OutputStream out = response.getOutputStream();
			wb.write(out);
		}
	}

	public static void writeExcelPP(String pathname, List<String> headList, List<Map<String, Object>> dataList, List<String> numberList, HttpServletResponse response)
			throws IOException {
			Workbook wb = new XSSFWorkbook();
			CellStyle style = getCellStyle(wb);
			Sheet sheet = wb.createSheet();
			/**
			 * 设置Excel表的第一行即表头
			 */
			Row row = sheet.createRow(0);
			for (int i = 0; i < headList.size(); i++) {
				Cell headCell = row.createCell(i);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);// 设置表头样式
				headCell.setCellValue(String.valueOf(headList.get(i)));
			}

			for (int i = 0; i < dataList.size(); i++) {
				Row rowdata = sheet.createRow(i + 1);// 创建数据行
				Map<String, Object> mapdata = dataList.get(i);
				Iterator it = mapdata.keySet().iterator();
				int j = 0;
				while (it.hasNext()) {
					String head = (String) it.next();
					String strdata = String.valueOf(mapdata.get(head));
					Cell celldata = rowdata.createCell(j);
					celldata.setCellType(Cell.CELL_TYPE_STRING);
					celldata.setCellValue(strdata);
					
					if(numberList.contains(head)){
						Boolean isNum = false;//data是否为数值型
		                Boolean isInteger=false;//data是否为整数
		                Boolean isPercent=false;//data是否为百分数
		                if (StringUtils.isNotNull(strdata)) {
		                    //判断data是否为数值型
		                    isNum = strdata.matches("^(-?\\d+)(\\.\\d+)?$");
		                    //判断data是否为整数（小数部分是否为0）
		                    isInteger=strdata.matches("^[-\\+]?[\\d]*$");
		                    //判断data是否为百分数（是否包含“%”）
		                    isPercent=strdata.contains("%");
		                }

		                //如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
		                CellStyle cellStyle = wb.createCellStyle();  
		                if (isNum && !isPercent) {
		                    DataFormat df = wb.createDataFormat(); // 此处设置数据格式
		                    if (isInteger) {
		                    	cellStyle.setDataFormat(df.getFormat("#0"));//数据格式只显示整数
		                    }else{
		                    	if((strdata.length()-strdata.lastIndexOf(".")-1)<=2){
		                    		cellStyle.setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点
		                    	}else{
		                    		String numberFmt = "#,##0.";
		                    		int numberLenght = strdata.length()-strdata.lastIndexOf(".")-1;
		                    		for (int k = 0; k < numberLenght; k++) {
		                    			numberFmt+="0";
									}
		                    		cellStyle.setDataFormat(df.getFormat(numberFmt));//保留两位小数点
		                    	}
		                    	
		                    }           
		                    if(Double.parseDouble(strdata)!=0){
		                    	// 设置单元格格式
			                    celldata.setCellStyle(cellStyle);
			                    // 设置单元格内容为double类型
			                    celldata.setCellValue(Double.parseDouble(strdata));
		                    }else{
		                    	// 设置单元格格式
			                    celldata.setCellStyle(cellStyle);
			                    // 设置单元格内容为double类型
			                    celldata.setCellValue("");
		                    }
		                    
		                } else {
		                	celldata.setCellType(Cell.CELL_TYPE_STRING);
		                    // 设置单元格内容为字符型
		                	celldata.setCellValue(strdata);
		                }
					}else{
						celldata.setCellType(Cell.CELL_TYPE_STRING);
						celldata.setCellValue(strdata);
					}
					
					j++;
				}
			}
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(pathname.getBytes("UTF-8"), "ISO8859-1"));
			OutputStream out = response.getOutputStream();
			wb.write(out);
	}
	
	public static void writeExcelSS(String pathname, List<String> headList, List<List<String>> dataList, HttpServletResponse response)
			throws IOException {
			Workbook wb = new XSSFWorkbook();
			CellStyle style = getCellStyle(wb);
			Sheet sheet = wb.createSheet();
			/**
			 * 设置Excel表的第一行即表头
			 */
			Row row = sheet.createRow(0);
			for (int i = 0; i < headList.size(); i++) {
				Cell headCell = row.createCell(i);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);// 设置表头样式
				headCell.setCellValue(String.valueOf(headList.get(i)));
			}
			for (int i = 0; i < dataList.size(); i++) {
				List<String> mapData = dataList.get(i);
				Row rowData = sheet.createRow(i + 1);// 创建数据行
				for (int j = 0; j < mapData.size(); j++) {
					Cell celldata = rowData.createCell(j);
					celldata.setCellType(Cell.CELL_TYPE_STRING);
					celldata.setCellValue(mapData.get(j));
				}
			}
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(pathname.getBytes("UTF-8"), "ISO8859-1"));
			OutputStream out = response.getOutputStream();
			wb.write(out);
	}

	public static void writeExcelDisk(String pathname, List<String> headList, List<Map<String, Object>> dataList, List<String> numberList)
			throws IOException {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		/**
		 * 设置Excel表的第一行即表头
		 */
		Row row = sheet.createRow(0);
		for (int i = 0; i < headList.size(); i++) {
			Cell headCell = row.createCell(i);
			headCell.setCellType(Cell.CELL_TYPE_STRING);
			headCell.setCellValue(String.valueOf(headList.get(i)));
		}

		for (int i = 0; i < dataList.size(); i++) {
			Row rowdata = sheet.createRow(i + 1);// 创建数据行
			Map<String, Object> mapdata = dataList.get(i);
			Iterator it = mapdata.keySet().iterator();
			int j = 0;
			while (it.hasNext()) {
				String head = (String) it.next();
				String strdata = String.valueOf(mapdata.get(head));
				Cell celldata = rowdata.createCell(j);
				celldata.setCellType(Cell.CELL_TYPE_STRING);
				celldata.setCellValue(strdata);

				if(numberList.contains(head)){
					Boolean isNum = false;//data是否为数值型
					Boolean isInteger=false;//data是否为整数
					Boolean isPercent=false;//data是否为百分数
					if (StringUtils.isNotNull(strdata)) {
						//判断data是否为数值型
						isNum = strdata.matches("^(-?\\d+)(\\.\\d+)?$");
						//判断data是否为整数（小数部分是否为0）
						isInteger=strdata.matches("^[-\\+]?[\\d]*$");
						//判断data是否为百分数（是否包含“%”）
						isPercent=strdata.contains("%");
					}

					//如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
					CellStyle cellStyle = wb.createCellStyle();
					if (isNum && !isPercent) {
						DataFormat df = wb.createDataFormat(); // 此处设置数据格式
						if (isInteger) {
							cellStyle.setDataFormat(df.getFormat("#0"));//数据格式只显示整数
						}else{
							if((strdata.length()-strdata.lastIndexOf(".")-1)<=2){
								cellStyle.setDataFormat(df.getFormat("#,##0.00"));//保留两位小数点
							}else{
								String numberFmt = "#,##0.";
								int numberLenght = strdata.length()-strdata.lastIndexOf(".")-1;
								for (int k = 0; k < numberLenght; k++) {
									numberFmt+="0";
								}
								cellStyle.setDataFormat(df.getFormat(numberFmt));//保留两位小数点
							}

						}
						if(Double.parseDouble(strdata)!=0){
							// 设置单元格格式
							celldata.setCellStyle(cellStyle);
							// 设置单元格内容为double类型
							celldata.setCellValue(Double.parseDouble(strdata));
						}else{
							// 设置单元格格式
							celldata.setCellStyle(cellStyle);
							// 设置单元格内容为double类型
							celldata.setCellValue("");
						}

					} else {
						celldata.setCellType(Cell.CELL_TYPE_STRING);
						// 设置单元格内容为字符型
						celldata.setCellValue(strdata);
					}
				}else{
					celldata.setCellType(Cell.CELL_TYPE_STRING);
					celldata.setCellValue(strdata);
				}

				j++;
			}
		}
		FileOutputStream output=new FileOutputStream(pathname);
		wb.write(output);
		output.flush();
		output.close();
	}

}
