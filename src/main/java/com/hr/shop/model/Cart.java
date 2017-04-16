package com.hr.shop.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;
import com.hr.shop.validatorInterface.ValidInterface;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;


/**
 * @author hjc
 * 购物车类
 */
@Entity
@Table(name = "cart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Cart implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.summary.class})
	@Min(value = 1 ,groups = {ValidInterface.class})
	private Integer id;

	/**
	 * 数量
	 */
	@Column(name = "number")
	@JsonView({View.summary.class})
	@Min(value = 1 ,groups = {ValidInterface.class})
	private Integer number;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ptid")
	@JsonView({View.summary.class})
	private Protype protype;

	@ManyToOne()
	@JoinColumn(name="uid")
	private User user;

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
	public Cart() {
	}

	/** minimal constructor */
	public Cart(Integer number) {
		this.number = number;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Protype getProtype() {
		return protype;
	}

	public void setProtype(Protype protype) {
		this.protype = protype;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}
}