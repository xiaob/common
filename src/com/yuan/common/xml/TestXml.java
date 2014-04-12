package com.yuan.common.xml;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
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
//		Jaxb.write(new User(), "d:/jaxb.xml");
		User user = Jaxb.parse(User.class, new FileInputStream("d:/jaxb.xml"));
		System.out.println(user.getSex());
		System.out.println(user.getBirthday());
		System.out.println(user.getStus().get(0).getName());
	}

}

@XmlRootElement(name="user")
class User{
	@XmlAttribute(name="sex")
	private String sex="man";
	
	@XmlAttribute
	private int age=20;
	
	@XmlElement(name="birthday")
	private String birthday="wang";
	
	@XmlElementWrapper(name="addresses")
	@XmlElement(name="address")
	private List<String> addresses = new ArrayList<String>();
	
	@XmlElementWrapper(name="stus")
	@XmlElement(name="stu") 
	private List<Stu> stus = new ArrayList<Stu>();
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date date = new Date();

	public User() {
		super();
		addresses.add("111");
		addresses.add("222");
		stus.add(new Stu(1, "zhang"));
		stus.add(new Stu(2, "li"));
	}

	public User(String sex, int age, String birthday) {
		super();
		this.sex = sex;
		this.age = age;
		this.birthday = birthday;
	}
	
	@XmlInit
	public void init(){
		System.out.println("init ...");
	}
	
	public String getSex() {
		return sex;
	}

//	public void setSex(String sex) {
//		this.sex = sex;
//	}

	public int getAge() {
		return age;
	}

//	public void setAge(int age) {
//		this.age = age;
//	}

	public String getBirthday() {
		return birthday;
	}

//	public void setBirthday(String birthday) {
//		this.birthday = birthday;
//	}

//	@XmlList
	public List<String> getAddresses() {
		return addresses;
	}

//	public void setAddresses(List<String> addresses) {
//		this.addresses = addresses;
//	}

	public Date getDate() {
		return date;
	}

//	public void setDate(Date date) {
//		this.date = date;
//	}
	
	public List<Stu> getStus() {
		return stus;
	}

//	public void setStus(List<Stu> stus) {
//		this.stus = stus;
//	}

	public static class Stu{
		@XmlElement(name="id")
		private int id;
		
		@XmlElement(name="name")
		private String name;
		
		public Stu(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		
		public Stu() {
			super();
		}

		public int getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
	}

}
