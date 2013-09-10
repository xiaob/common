package com.yuan.common.brpc.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoTest {

	
	public PageModel demo1() throws Exception{
		PojoModel model1 = new PojoModel(1, "wang", 12.34d, new Date());
		PojoModel model2 = new PojoModel(2, "zhang", 56.78d, new Date());
		
		List<PojoModel> list = new ArrayList<PojoModel>();
		list.add(model1);
		list.add(model2);
		
		Map<String, PojoModel> map = new HashMap<String, PojoModel>();
		map.put("model1", model1);
		map.put("model2", model2);
		
		PageModel pageModel = new PageModel();
		pageModel.setList(list);
		pageModel.setMap(map);
		return pageModel;
	}
	
//	public DataObject demo2(){
//		DataObject model1 = new DataObject();
//		model1.setValue("id", 1);
//		model1.setValue("name", "wang");
//		model1.setValue("price", 12.34d);
//		model1.setValue("birthday", new Date());
//		DataObject model2 = new DataObject();
//		model2.setValue("id", 2);
//		model2.setValue("name", "zhang");
//		model2.setValue("price", 56.78d);
//		model2.setValue("birthday", new Date());
//		
//		List<DataObject> list = new ArrayList<DataObject>();
//		list.add(model1);
//		list.add(model2);
//		
//		Map<String, DataObject> map = new HashMap<String, DataObject>();
//		map.put("model1", model1);
//		map.put("model2", model2);
//		
//		DataObject pageModel = new DataObject();
//		pageModel.setValue("list", list);
//		pageModel.setValue("map", map);
//		log.info("pageModel = " + pageModel);
//		return pageModel;
//	}
	
	public List<PojoModel> demo3(){
		PojoModel model1 = new PojoModel(1, "wang", 12.34d, new Date());
		PojoModel model2 = new PojoModel(2, "zhang", 56.78d, new Date());
		
		List<PojoModel> list = new ArrayList<PojoModel>();
		list.add(model1);
		list.add(model2);
		
		return list;
	}
	
	public Map<String, PojoModel> demo4(){
		PojoModel model1 = new PojoModel(1, "wang", 12.34d, new Date());
		PojoModel model2 = new PojoModel(2, "zhang", 56.78d, new Date());
		
		Map<String, PojoModel> map = new HashMap<String, PojoModel>();
		map.put("model1", model1);
		map.put("model2", model2);
		
		return map;
	}
	
//	public List<DataObject> demo5(){
//		DataObject model1 = new DataObject();
//		model1.setValue("id", 1);
//		model1.setValue("name", "wang");
//		model1.setValue("price", 12.34d);
//		model1.setValue("birthday", new Date());
//		DataObject model2 = new DataObject();
//		model2.setValue("id", 2);
//		model2.setValue("name", "zhang");
//		model2.setValue("price", 56.78d);
//		model2.setValue("birthday", new Date());
//		
//		List<DataObject> list = new ArrayList<DataObject>();
//		list.add(model1);
//		list.add(model2);
//		
//		return list;
//	}
//	
//	public Map<String, DataObject> demo6(){
//		DataObject model1 = new DataObject();
//		model1.setValue("id", 1);
//		model1.setValue("name", "wang");
//		model1.setValue("price", 12.34d);
//		model1.setValue("birthday", new Date());
//		DataObject model2 = new DataObject();
//		model2.setValue("id", 2);
//		model2.setValue("name", "zhang");
//		model2.setValue("price", 56.78d);
//		model2.setValue("birthday", new Date());
//		
//		Map<String, DataObject> map = new HashMap<String, DataObject>();
//		map.put("model1", model1);
//		map.put("model2", model2);
//		
//		return map;
//	}
//	
//	public void demo7(Long id, Date time, DataObject model, List<DataObject> list){
//		System.out.println("id = " + id);
//		System.out.println("time = " + time);
//		System.out.println("model = " + model.toJson());
//		for(DataObject dataObject : list){
//			System.out.println("list dataObject = " + dataObject.toJson());
//		}
//	}
	
	public void demo8(Long id, Date time, PojoModel model, List<PojoModel> list){
		System.out.println("id = " + id);
		System.out.println("time = " + time);
		System.out.println("model = " + model.getBirthday());
		for(PojoModel dataObject : list){
			System.out.println("list dataObject = " + dataObject.getBirthday());
		}
	}
	
}
