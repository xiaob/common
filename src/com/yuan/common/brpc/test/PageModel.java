package com.yuan.common.brpc.test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PageModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long total;
	private List<PojoModel> list;
	private Map<String, PojoModel> map;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<PojoModel> getList() {
		return list;
	}
	public void setList(List<PojoModel> list) {
		this.list = list;
	}
	public Map<String, PojoModel> getMap() {
		return map;
	}
	public void setMap(Map<String, PojoModel> map) {
		this.map = map;
	}
	
}
