package com.yuan.common.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExcelTool {
	
	public static void splitExcel(String dir, String name, int size, int ignoreRows) throws IOException{
		File parentDir = new File(dir);
		ExcelParser ep = new ExcelParser(new File(parentDir, name + ".xls").getAbsolutePath());
		for(int i=0; i<ignoreRows; i++){
			if(ep.hasNext()){
				ep.next();
			}
		}
		int i = 0;
		int j = 0;
		int rowIndex = 0;
		ExcelWriter writer = null;
		while(ep.hasNext()){
			List<String> row = ep.next();
			if(i % size == 0){
				if(writer != null){
					writer.close();
				}
				j++;
				writer = new ExcelWriter(name, new File(parentDir, name+j+".xls").getAbsolutePath());
				rowIndex = 0;
				System.out.println("新建文件: " + name + j);
			}
			writer.createRow(rowIndex, row);
			rowIndex ++ ;
			i++;
		}
		if(writer != null){
			writer.close();
		}
		ep.close();
	}
	
}
