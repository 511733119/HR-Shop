package com.hr.shop.model;


import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.security.Timestamp;

/**
 * @author hjc
 * 管理员类
 */
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 管理员用户名
	 */
	@Column(name = "accountname", length = 20)
	private String accountname;

	/**
	 * 管理员密码
	 */
	@Column(name = "accountpwd", length = 20)
	private String accountpwd;

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
	public Account() {
	}

	/** full constructor */
	public Account(String accountname, String accountpwd) {
		this.accountname = accountname;
		this.accountpwd = accountpwd;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAccountpwd() {
		return accountpwd;
	}

	public void setAccountpwd(String accountpwd) {
		this.accountpwd = accountpwd;
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
		return super.toString();
	}
}