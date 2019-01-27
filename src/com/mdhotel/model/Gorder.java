package com.mdhotel.model;

import java.util.Date;

public class Gorder {
	private String code;
	private String goods;
	private int count;
	private int price;
	private Date time;
	private int type;
	private String shop;
	public Gorder() {
		super();
	}
	public Gorder(String goods, int count, int price, int type) {
		super();
		this.goods = goods;
		this.count = count;
		this.price = price;
		this.type = type;
	}

	public Gorder(String goods, int count, int price, int type, String shop) {
		super();
		this.goods = goods;
		this.count = count;
		this.price = price;
		this.type = type;
		this.shop = shop;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
}
