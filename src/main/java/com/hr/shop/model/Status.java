package com.hr.shop.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @author hjc
 * 订单状态类
 */
@Entity
@Table(name = "status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Status implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(View.summary.class)
	private Integer id;
	/**
	 * 订单状态
	 */
	@Column(name = "status", length =10 )
	@JsonView(View.summary.class)
	private String status;

	// Constructors

	/** default constructor */
	public Status() {
	}

	/** full constructor */
	public Status(String status) {
		this.status = status;
	}

	public Status(Integer id) {
		this.id = id;
	}
	
	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}