package com.yuan.common.csv;

import java.util.List;

public class CsvTest {
	
	public static void main(String[] args) {
		String file = "D:\\workspace2\\vgos\\source\\trunk\\WebRoot\\WEB-INF\\templates\\jsy.csv";
	
		CsvParser p = new CsvParser(file, "GBK");
		while(p.hasNext()){
			List<String> row = p.next();
			System.out.println(p.getLineNumber() + " : " + row.get(0) + ", " + row.get(1));
		}
		p.close();
	}
	
}

