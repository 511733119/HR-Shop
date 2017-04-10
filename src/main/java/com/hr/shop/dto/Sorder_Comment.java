package com.hr.shop.dto;

public class Sorder_Comment {

	private int id;
	
	private String comment;

	private String name;
	
	public Sorder_Comment(int id) {
		this.id = id;
	}

	public Sorder_Comment(String comment) {
		this.comment = comment;
	}

	public Sorder_Comment(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
}
