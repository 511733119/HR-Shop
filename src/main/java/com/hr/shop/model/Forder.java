package com.hr.shop.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;
import com.hr.shop.validatorInterface.ValidInterface;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author hjc
 */
@Entity
@Table(name = "forder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Forder implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@JsonView(View.summary.class)
	@NotEmpty
	@Length(min=10,max=20 , groups = {ValidInterface.class})
	private String id;

	/**
	 * 用户名字
	 */
	@Column(name = "name", length =20 )
	@JsonView(View.summary.class)
	@NotEmpty
	@Length(min=1,max = 20, groups = {ValidInterface.class})
	private String name;

	/**
	 * 用户电话号码
	 */
	@Column(name = "phone", length = 20)
	@JsonView(View.summary.class)
	@NotEmpty
	@Length(min=11,max = 11, groups = {ValidInterface.class})
	private String phone;

	/**
	 * 订单备注
	 */
	@Column(name = "remark", length = 20)
	@JsonView(View.summary.class)
	@Length(min=0,max = 50, groups = {ValidInterface.class})
	private String remark;

	/**
	 * 订单总金额
	 */
	@Column(name = "total", precision=8,scale=2)
	@JsonView(View.summary.class)
	@NotNull
	@DecimalMin(value = "0.00")
	private BigDecimal total;

	/**
	 * 收货地址
	 */
	@Column(name = "address", length = 200)
	@JsonView(View.summary.class)
	@NotEmpty
	@Length(min=2,max = 50, groups = {ValidInterface.class})
	private String address;

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

	/**
	 * 支付日期
	 */
	@Column(name = "pay_date" , length = 19)
	private Timestamp pay_date;
	
	/**
	 * 对应用户表主键
	 */
	@ManyToOne
	@JoinColumn(name="uid")
	private User user;

	/**
	 * 对应状态表主键
	 */
	@ManyToOne()
	@JoinColumn(name="sid")
	@JsonView(View.summary.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Status status;

	/**
	 * 订单项集合
	 */
	@OneToMany(targetEntity=Sorder.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "fid")
	@JsonView(View.summary.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Sorder> sorderSet;

	/**
	 * 订单是否已删除标志
	 */
	@Column(name = "flag", length = 1)
	private int flag;

	/** default constructor */
	public Forder() {
	}

	public Forder(Set<Sorder> sorderSet) {
		this.sorderSet = sorderSet;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<Sorder> getSorderSet() {
		return sorderSet;
	}

	public void setSorderSet(Set<Sorder> sorderSet) {
		this.sorderSet = sorderSet;
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
		return "Forder [id=" + id + ", name=" + name + ", phone=" + phone + ", remark=" + remark + ", total=" + total
				+ ", address=" + address + ", create_date=" + create_date + ", update_date=" + update_date
				+ ", pay_date=" + pay_date + ", user=" + user + ", status=" + status + ", sorderSet=" + sorderSet
				+ ", flag=" + flag + "]";
	}
}