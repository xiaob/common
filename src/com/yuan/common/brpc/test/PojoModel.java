package com.yuan.common.brpc.test;

import java.io.Serializable;
import java.util.Date;

public class PojoModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Double price;
	private Date birthday;
	
	public PojoModel(){
		
	}
	
	public PojoModel(Integer id, String name, Double price, Date birthday) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.birthday = birthday;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
