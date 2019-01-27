package com.mdhotel.model;

import java.util.Date;

public class Staff {
	private String login;
	private String name;
	private String pwd;
	private int type;
	private int age;
	private String sex;
	private Date time;
	private String tel;
	private String address;
	private String remark;
	private int state;
	private String shop;
	
	
	public Staff() {
		
	}
	public Staff(String login, String pwd) {
		super();
		this.login = login;
		this.pwd = pwd;
	}
	public Staff(String login, String pwd,int type) {
		this.login = login;
		this.pwd = pwd;
		this.type=type;
	}
	public Staff(String login, String name, String pwd, int type) {
		this.login = login;
		this.name = name;
		this.pwd = pwd;
		this.type = type;
	}
	public Staff(String login, String name, int age, String sex, Date time, String tel, String address, String remark) {
		super();
		this.login = login;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.time = time;
		this.tel = tel;
		this.address = address;
		this.remark = remark;
	}
	public Staff(String login, String name, String pwd, int type, int age, String sex, Date time, String tel,
			String address, String remark) {
		super();
		this.login = login;
		this.name = name;
		this.pwd = pwd;
		this.type = type;
		this.age = age;
		this.sex = sex;
		this.time = time;
		this.tel = tel;
		this.address = address;
		this.remark = remark;
	}
	
	public Staff(String login, String name, String pwd, int type, int age, String sex, Date time, String tel,
			String address, String remark, String shop) {
		super();
		this.login = login;
		this.name = name;
		this.pwd = pwd;
		this.type = type;
		this.age = age;
		this.sex = sex;
		this.time = time;
		this.tel = tel;
		this.address = address;
		this.remark = remark;
		this.state = state;
		this.shop = shop;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	
}
