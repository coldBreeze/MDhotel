package com.mdhotel.model;

public class Goods {
	private String code;
	private String name;
	private int count;
	private int price;
	
	public Goods() {
	}
	public Goods(String code, int count) {
		this.code = code;
		this.count = count;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
