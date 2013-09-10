package com.yuan.common.brpc.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IDemoTest {

	public static final String CLASSNAME = "com.yuan.common.brpc.test.DemoTest";
	
	public PageModel demo1();
	public List<PojoModel> demo3();
	public Map<String, PojoModel> demo4();
	public void demo8(Long id, Date time, PojoModel model, List<PojoModel> list);
	
}
