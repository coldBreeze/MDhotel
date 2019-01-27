package com.mdhotel.model;

public class Passenger {
	private String code;
	private String name;
	private int age;
	private String sex;
	private String idcard;
	private String tel;
	private String remark;
	private int state;
	
	public Passenger() {
		super();
	}
	
	public Passenger(String name, int age, String sex, String idcard, String tel, String remark) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.idcard = idcard;
		this.tel = tel;
		this.remark = remark;
	}
	
	public Passenger(String code, String name, int age, String sex, String idcard, String tel, String remark) {
		super();
		this.code = code;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.idcard = idcard;
		this.tel = tel;
		this.remark = remark;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
