package com.yuan.common.xml;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class TestXml {

	public static void main(String[] args)throws Exception {
		test2();
	}
	
	public static void test1()throws Exception{
		Dom dom = Dom.getRoot(new FileInputStream("d:/test/Application.xml"));
		System.out.println(dom.element("intro").hasChildren());
		System.out.println(dom.element("description").hasChildren());
		System.out.println(dom.element("test").hasChildren());
	}
	
	public static void test2()throws Exception{
		Jaxb.write(new User(), "d:/jaxb.xml");
	}

}

@XmlRootElement
class User{
	@XmlAttribute
	private String sex="man";
	
	@XmlAttribute
	private int age=20;
	
	private String birthday="wang";
	
	private List<String> addresses = new ArrayList<String>();
	
	private Date date = new Date();

	public User() {
		super();
		addresses.add("111");
		addresses.add("222");
	}

	public User(String sex, int age, String birthday) {
		super();
		this.sex = sex;
		this.age = age;
		this.birthday = birthday;
	}
	
//	public String getSex() {
//		return sex;
//	}
//
//	public void setSex(String sex) {
//		this.sex = sex;
//	}
//
//	public int getAge() {
//		return age;
//	}
//
//	public void setAge(int age) {
//		this.age = age;
//	}
//
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@XmlList
	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
