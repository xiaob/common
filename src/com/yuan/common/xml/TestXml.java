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

@XmlRootElement
class User{
	private String sex="man";
	
	private int age=20;
	
	private String birthday="wang";
	
	private List<String> addresses = new ArrayList<String>();
	private List<Stu> stus = new ArrayList<Stu>();
	
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
	
	@XmlAttribute
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@XmlAttribute
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

//	@XmlList
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
	
	@XmlElementWrapper(name="stus")
	@XmlElement(name="stu") 
	public List<Stu> getStus() {
		return stus;
	}

	public void setStus(List<Stu> stus) {
		this.stus = stus;
	}

	public static class Stu{
		private int id;
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
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}

}
