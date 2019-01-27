package com.mdhotel.model;

public class Shop {
	private String code;
	private String name;
	private String address;
	private String time;
	private int state;
	private String remark;
	public Shop() {
		super();
	}
	
	public Shop(String code, String name, String address, String time, String remark) {
		super();
		this.code = code;
		this.name = name;
		this.address = address;
		this.time = time;
		this.remark = remark;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
