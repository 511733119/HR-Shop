package com.hr.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author hjc
 * 用户关注商铺类
 */
@Entity
@Table(name = "user_follow_business")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class User_follow_Business implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.summary.class)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="uid")
	@JsonView(View.summary.class)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bid")
	@JsonView(View.summary.class)
	private Business business;

	/**
	 * 创建日期
	 */
	@Column(name = "create_date", length =19 )
	private Timestamp create_date;

	/**
	 * 更新日期
	 */
	@JsonIgnore
	@Column(name = "update_date", length =19 )
	private Timestamp update_date;

	/** default constructor */
	public User_follow_Business() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

}