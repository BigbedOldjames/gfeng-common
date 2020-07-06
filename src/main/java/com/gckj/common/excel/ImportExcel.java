package com.omysoft.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.omysoft.common.utils.StringUtils;

/**
 * Excel导入
 * @author yy
 * @date 2017-8-10
 */
public class ImportExcel<T extends ExcelBaseEntity> {

	private Class<T> clazz;
	
	private Workbook book;
	
	/**
	 * 工作表对象
	 */
	private Sheet sheet;
	
	/**
	 * 标题行号
	 */
	private int headerNum;

	public ImportExcel(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 导入Excel文件
	 * @param pathName
	 * 		完整的文件路径
	 * @param headerNum
	 * 		表头所在的行
	 * @param sheetIndex
	 * 		工作表的索引
	 */
	public List<T> importExcel(String pathName, int headerNum, int sheetIndex) throws IOException, InstantiationException, IllegalAccessException {
		File f = new File(pathName);   
        return importExcel(pathName, new FileInputStream(f), headerNum, sheetIndex);
	}
	
	/**
	 * 导入Excel文件
	 * @param pathName
	 * 		文档的名称
	 * @param contentType
	 * 		文档的类型
	 * @param headerNum
	 * 		表头所在的行
	 * @param sheetIndex
	 * 		工作表的索引
	 */
	public List<T> importExcel(String pathName, InputStream input, int headerNum, int sheetIndex) throws IOException, InstantiationException, IllegalAccessException {
		// 校验Excel文件
        if (StringUtils.isBlank(pathName)) {
			throw new RuntimeException("导入文档名称为空!");
		} else if (pathName.toLowerCase().endsWith("xls") || pathName.toLowerCase().endsWith("xlsx")) {
			return importExcel(input, headerNum, sheetIndex);
		} else {
			throw new RuntimeException("Excel文档格式不正确!");
		}
		
	}
	
	/**
	 * 导入Excel文件
	 * @param filePath
	 * 		完整的文件路径
	 * @param headerNum
	 * 		表头所在的行
	 * @param sheetIndex
	 * 		工作表的索引
	 */
	public List<T> importExcel(InputStream input, int headerNum, int sheetIndex) throws IOException, InstantiationException, IllegalAccessException {
		this.book = new XSSFWorkbook(input);
		if (this.book.getNumberOfSheets() < sheetIndex) {
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheet = book.getSheetAt(sheetIndex);
		this.headerNum = headerNum;
		List<T> list = new ArrayList<T>();
		
		Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
		int importLen = 0;
		Field[] allFields = clazz.getDeclaredFields();// 获取类的字段属性
		for (Field field : allFields) {
			if (field.isAnnotationPresent(ExcelField.class)) {
				ExcelField attr = field.getAnnotation(ExcelField.class);
				int col = getExcelCol(attr.column());
				field.setAccessible(true);
				fieldsMap.put(col, field);
				importLen++;
			}
		}
		for (int i = this.getDataRowNum(); i <= this.getLastDataRowNum(); i++) {
			Row row = this.getRow(i);
			T entity = clazz.newInstance();
			entity.setRowIndex(i + 1);
			for (int j = 0; j < importLen; j++) {
				if(row!=null){
					Cell cell = row.getCell(j);
					if (cell != null){
						String c = "";
						/* isCellDateFormatted 判断是字符串或（Cell.CELL_TYPE_NUMERIC）是数值格式 */
						if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC&&HSSFDateUtil.isCellDateFormatted(cell)) {
					        Date date = cell.getDateCellValue();//转换为日期格式
					        c = DateFormatUtils.format(date, "yyyy-MM-dd");
					    }else{
					    	row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							c = row.getCell(j).getStringCellValue();
							if (c.equals("")) {
								continue;
							}
					    }
						
						Field field = fieldsMap.get(j);
						Class<?> fieldType = field.getType();
						if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
							field.set(entity, Integer.parseInt(c));
						} else if (String.class == fieldType) {
							field.set(entity, String.valueOf(c));
						} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
							field.set(entity, Long.valueOf(c));
						} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
							field.set(entity, Float.valueOf(c));
						} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
							field.set(entity, Short.valueOf(c));
						} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
							field.set(entity, Double.valueOf(c));
						} else if (Character.TYPE == fieldType) {
							if ((c != null) && (c.length() > 0)) {
								field.set(entity, Character.valueOf(c.charAt(0)));
							}
						}
					}
				}
			}
			if (entity != null) {
				list.add(entity);
			}
		}
		return list;
	}
	
	/**
	 * 获取行对象
	 * @param rownum
	 * @return
	 */
	public Row getRow(int rownum){
		return this.sheet.getRow(rownum);
	}

	/**
	 * 获取数据行号
	 * @return
	 */
	public int getDataRowNum(){
		return headerNum+1;
	}
	
	/**
	 * 获取最后一个数据行号
	 * @return
	 */
	public int getLastDataRowNum(){
		return this.sheet.getLastRowNum()+headerNum;
	}
	
	/**
	 * 获取最后一个列号
	 * @return
	 */
	public int getLastCellNum(){
		return this.getRow(headerNum).getLastCellNum();
	}
	
	/**
	 * 获取excel转化到数字 A->1 B->2
	 * @param col
	 * @return 
	 */
	public static int getExcelCol(String col) {
		col = col.toUpperCase();
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}

}
