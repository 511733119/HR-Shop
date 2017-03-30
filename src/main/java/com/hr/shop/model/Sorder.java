package com.hr.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author hjc
 * 订单项类
 */
@Entity
@Table(name = "sorder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Sorder implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.summary.class)
	private Integer id;

	/**
	 * 单项价格
	 */
	@Column(name = "price", precision=8,scale=2 )
	@JsonView(View.summary.class)
	private BigDecimal price;

	/**
	 * 数量
	 */
	@Column(name = "number")
	@JsonView(View.summary.class)
	private Integer number;

	@ManyToOne()
	@JoinColumn(name="ptid")
	@JsonView(View.son.class)
	private Protype protype;

	@ManyToOne()
	@JoinColumn(name="fid")
	private Forder forder;

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

	// Constructors

	/** default constructor */
	public Sorder() {
	}

	/** minimal constructor */
	public Sorder(Integer number) {
		this.number = number;
	}

	public Forder getForder() {
		return forder;
	}

	public void setForder(Forder forder) {
		this.forder = forder;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNumber() {
		return this.number;
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

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public Timestamp getCreate_date() {
		return create_date;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}
}