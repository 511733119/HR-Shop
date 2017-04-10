package com.hr.shop.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 追加评论存储的图片实体类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/30 15:29
 */
@Entity
@Table(name = "append_comment_pic")
public class Append_Comment_Pic implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.summary.class})
	private Integer id;

	/**
	 * 图片url
	 */
	@Column(name = "pic", length = 100)
	@JsonView({View.summary.class})
	private String pic;

	@ManyToOne()
	@JoinColumn(name="append_comment_id")
	private Comment append_comment;

	/**
	 * 创建日期
	 */
	@Column(name = "create_date", length = 19)
	private Timestamp create_date;

	/**
	 * 更新日期
	 */
	@Column(name = "update_date", length = 19)
	private Timestamp update_date;
	// Constructors

	/** default constructor */
	public Append_Comment_Pic() {
	}

	/** full constructor */
	public Append_Comment_Pic(String pic) {
		this.pic = pic;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public void setAppend_comment(Comment append_comment) {
		this.append_comment = append_comment;
	}

	public Comment getAppend_comment() {
		return append_comment;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

}