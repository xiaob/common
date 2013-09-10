package com.yuan.common.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelWriter {
	
	private static final Logger log = LoggerFactory.getLogger(ExcelWriter.class);
	
	private String excelFile;
	private HSSFWorkbook wb = null;
	private HSSFSheet sheet = null;
	
	public ExcelWriter(String sheetName){
		wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
		sheet = wb.createSheet(sheetName);//建立新的sheet对象
	}
	
	public ExcelWriter(String sheetName, String excelFile){
		this.excelFile = excelFile;
		wb = new HSSFWorkbook();//建立新HSSFWorkbook对象
		sheet = wb.createSheet(sheetName);//建立新的sheet对象
	}
	
	private HSSFCell createCell(HSSFRow row, int colIndex, Object value){
		HSSFCell cell = row.createCell(colIndex);
		
		if(value instanceof Number){//数字
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			Number n = (Number)value;
			cell.setCellValue(n.doubleValue());
		}else{//字符串
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString((String)value));
		}
		
		HSSFCellStyle cstyle = cell.getCellStyle();
		cstyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		cstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		return cell;
	}
	
	public void createRow(int rowIndex, List<? extends Object> colList){
		HSSFRow row = sheet.createRow((short)rowIndex);
		for(int i=0;i<colList.size(); i++){
			createCell(row, i, colList.get(i));
		}
	}
	
	/**
	 * 创建复杂表头
	 * @param rowIndex int
	 * @param colList Map<Integer, String>
	 */
	public void createHeader(int rowIndex, Map<Integer, String> colList){
		HSSFRow header = sheet.createRow((short)rowIndex);
		Iterator<Integer> it = colList.keySet().iterator();
		while(it.hasNext()){
			Integer cellIndex = it.next();
			createCell(header, cellIndex, colList.get(cellIndex));
		}
	}
	
	/**
	 * 合并单元格
	 * @param rowFrom int
	 * @param colFrom int
	 * @param rowTo int
	 * @param colTo int
	 */
	public void merge(int rowFrom, int colFrom, int rowTo, int colTo){
//		sheet.addMergedRegion(new Region(rowFrom,(short)colFrom,rowTo,(short)colTo));
		sheet.addMergedRegion(new CellRangeAddress(rowFrom, rowTo, colFrom, colTo));
	}
	
	public void close(){
		// Write the output to a file
		try {
			OutputStream os = new FileOutputStream(excelFile);
			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void writeToFile(String excelFile){
		// Write the output to a file
		try {
			OutputStream os = new FileOutputStream(excelFile);
			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void writeToStream(OutputStream os){
		try {
			wb.write(os);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

}
