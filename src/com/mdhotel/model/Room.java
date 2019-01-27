package com.mdhotel.model;

public class Room {
	private String code;
	private String name;
	private int cash;
	private int price;
	private int state;
	private String remark;
	public Room(String code, String name, int cash, int price, int state, String remark) {
		super();
		this.code = code;
		this.name = name;
		this.cash = cash;
		this.price = price;
		this.state = state;
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
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
