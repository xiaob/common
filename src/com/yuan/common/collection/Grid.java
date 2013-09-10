package com.yuan.common.collection;

import java.util.ArrayList;
import java.util.List;

public class Grid {

	private Integer total;
	private List<Object> beans = new ArrayList<Object>();
	
	public void addBean(Object bean){
		beans.add(bean);
	}
	
	public List<Object> getBeans() {
		return beans;
	}

	public void setBeans(List<Object> beans) {
		this.beans = beans;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
