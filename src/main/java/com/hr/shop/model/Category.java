package com.hr.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

import javax.persistence.*;


/**
 * @author hjc
 * 商品种类类
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Category implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 类型名称
	 */
	@Column(name = "type", length =20 )
	private String type;

	/**
	 * 是否上架
	 */
	@Column(name = "hot")
	private Boolean hot;

	/**
	 * 创建日期
	 */
	@JsonIgnore
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
	public Category() {
	}

	/** full constructor */
	public Category(String type, Boolean hot) {
		this.type = type;
		this.hot = hot;
	}
	
	/** full constructor */
	public Category(Integer id,String type, Boolean hot) {
		this.id=id;
		this.type = type;
		this.hot = hot;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getHot() {
		return this.hot;
	}

	public void setHot(Boolean hot) {
		this.hot = hot;
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

	@Override
	public String toString() {
		return super.toString();
	}
}