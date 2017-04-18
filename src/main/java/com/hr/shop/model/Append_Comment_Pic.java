package com.hr.shop.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;

import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 追加评论存储的图片实体类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/30 15:29
 */
@Entity
@Table(name = "append_comment_pic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="append_comment_id")
	private Append_Comment append_comment;

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

	public void setAppend_comment(Append_Comment append_comment) {
		this.append_comment = append_comment;
	}

	public Append_Comment getAppend_comment() {
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

	@Override
	public String toString() {
		return "Append_Comment_Pic [id=" + id + ", pic=" + pic + ", append_comment=" + append_comment + ", create_date="
				+ create_date + ", update_date=" + update_date + "]";
	}

}