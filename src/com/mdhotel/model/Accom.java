package com.mdhotel.model;

import java.util.Date;

public class Accom {
	private String code;
	private String room;
	private String pass;
	private Date intime;
	private Date outime;
	private String order;
	private int price;
	private String staff;
	private String remark;
	
	public Accom(String code, String order, int price) {
		super();
		this.code = code;
		this.order = order;
		this.price = price;
	}

	public Accom(String code, Date outime, int price, String remark) {
		super();
		this.code = code;
		this.outime = outime;
		this.price = price;
		this.remark = remark;
	}

	public Accom(String code, String room, String pass, Date intime, Date outime, int price, String staff,String remark) {
		super();
		this.code = code;
		this.room = room;
		this.pass = pass;
		this.intime = intime;
		this.outime = outime;
		this.price = price;
		this.staff = staff;
		this.remark=remark;
	}
	
	public Accom(String code, String room, String pass, Date intime, Date outime, String order, int price, String staff,
			String remark) {
		super();
		this.code = code;
		this.room = room;
		this.pass = pass;
		this.intime = intime;
		this.outime = outime;
		this.order = order;
		this.price = price;
		this.staff = staff;
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public Date getOutime() {
		return outime;
	}

	public void setOutime(Date outime) {
		this.outime = outime;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
