package com.yuan.common.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	
	public static void main(String[] args)throws IOException {
		test3();
	}
	
	public static void test1(){
		try {
			ExcelParser ep = new ExcelParser("d:/秦皇岛EC统计报表.xls");
			while(ep.hasNext()){
				List<String> row = ep.next();
				System.out.println(row.get(0) + ", " + row.get(1));
			}
			ep.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void test2(){
		ExcelWriter ew = new ExcelWriter("测试", "d:/a.xls");
		Map<Integer, String> headerMap = new HashMap<Integer, String>();
		headerMap.put(0, "EC名称");
		headerMap.put(1, "用户数");
		headerMap.put(4, "登录次数");
		headerMap.put(7, "页面浏览量");
		headerMap.put(10, "政府内部功能使用量");
		headerMap.put(15, "信息系统功能使用量");
		headerMap.put(19, "通信流量");
		ew.createHeader(0, headerMap);
		
		headerMap.clear();
		headerMap.put(19, "短信");
		headerMap.put(21, "语音");
		ew.createHeader(1, headerMap);
		
		ew.merge(0, 0, 1, 0);
		ew.merge(0, 1, 1, 3);
		ew.merge(0, 4, 1, 6);
		ew.merge(0, 7, 1, 9);
		ew.merge(0, 10,1, 14);
		ew.merge(0, 15, 1, 18);
		ew.merge(0, 19, 0, 23);
		ew.merge(1, 19, 1, 20);
		ew.merge(1, 21, 1, 23);
		
		String[] cols = {"EC名称",
				"内部办公成员数", "农户数","企业数","WEB","WAP","总量","WEB","WAP","总量",
				"公文流转","任务督办","数据采集","下发通知","信息群组","采集条数","通过审核条数",
				"发布条数","审核通过率","短信下行","短信上行","次数","方数(人)","秒"
		};
		ew.createRow(2, Arrays.asList(cols));
		
		List<Integer> row3 = new ArrayList<Integer>();
		for(int i=0;i<24;i++){
			row3.add(10);
		}
		ew.createRow(3, row3);
		
		ew.close();
		
		System.out.println("11111111111111");
	}
	
	public static void test3() throws IOException{
		ExcelParser ep = new ExcelParser("D:\\workspace\\HXV\\ameba_db_server\\resource\\fishing_exp.xlsx");
		while(ep.hasNext()){
			List<String> row = ep.next();
			System.out.println(row.get(0) + ", " + row.get(1));
		}
		ep.close();
	}

}
