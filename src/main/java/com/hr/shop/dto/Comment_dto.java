package com.hr.shop.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.hr.shop.util.StringToTimestamp;

/**
 * 获取评论dto
 * @author hjc
 *
 */
public class Comment_dto implements Serializable{

	private int comment_id;
	private int star;
	private String comment;
	private Timestamp create_date;
	
	public Comment_dto() {
		super();
	}

	public Comment_dto(int comment_id, int star, String comment, Object create_date) {
		this.comment_id = comment_id;
		this.star = star;
		this.comment = comment;
		this.create_date = StringToTimestamp.stringToTimestamp(create_date.toString());
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}
	
}
