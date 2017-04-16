package com.hr.shop.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hr.shop.util.StringToTimestamp;

public class Comment_Page implements Serializable{

	private int sorder_id;
	private int sorder_number;
	private BigDecimal sorder_price;
	private String forder_id;
	private Timestamp forder_create_date;
	private int product_id;
	private String product_name;
	private BigDecimal product_price;
	private String product_remark;
	private int protype_id;
	private String protype_pic;
	private String protype_name;
	
	
	public Comment_Page() {
	}
	
	public Comment_Page(int sorder_id, int sorder_number, BigDecimal sorder_price, String forder_id, Object forder_create_date,
			int product_id, String product_name, BigDecimal product_price, String product_remark, int protype_id,
			String protype_pic, String protype_name) {
		this.sorder_id = sorder_id;
		this.sorder_number = sorder_number;
		this.sorder_price = sorder_price;
		this.forder_id = forder_id;
		this.forder_create_date = StringToTimestamp.stringToTimestamp(forder_create_date.toString());
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_price = product_price;
		this.product_remark = product_remark;
		this.protype_id = protype_id;
		this.protype_pic = protype_pic;
		this.protype_name = protype_name;
	}
	public int getSorder_id() {
		return sorder_id;
	}

	public void setSorder_id(int sorder_id) {
		this.sorder_id = sorder_id;
	}

	public int getSorder_number() {
		return sorder_number;
	}

	public void setSorder_number(int sorder_number) {
		this.sorder_number = sorder_number;
	}

	public BigDecimal getSorder_price() {
		return sorder_price;
	}

	public void setSorder_price(BigDecimal sorder_price) {
		this.sorder_price = sorder_price;
	}

	public String getForder_id() {
		return forder_id;
	}

	public void setForder_id(String forder_id) {
		this.forder_id = forder_id;
	}

	public Timestamp getForder_create_date() {
		return forder_create_date;
	}

	public void setForder_create_date(Timestamp forder_create_date) {
		this.forder_create_date = forder_create_date;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public BigDecimal getProduct_price() {
		return product_price;
	}

	public void setProduct_price(BigDecimal product_price) {
		this.product_price = product_price;
	}

	public String getProduct_remark() {
		return product_remark;
	}

	public void setProduct_remark(String product_remark) {
		this.product_remark = product_remark;
	}

	public int getProtype_id() {
		return protype_id;
	}

	public void setProtype_id(int protype_id) {
		this.protype_id = protype_id;
	}

	public String getProtype_pic() {
		return protype_pic;
	}

	public void setProtype_pic(String protype_pic) {
		this.protype_pic = protype_pic;
	}

	public String getProtype_name() {
		return protype_name;
	}

	public void setProtype_name(String protype_name) {
		this.protype_name = protype_name;
	}
}
