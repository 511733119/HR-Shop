package com.hr.shop.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;
import com.hr.shop.validatorInterface.ValidInterface;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * @author hjc
 * 用户类
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Min(value = 1 ,groups = {ValidInterface.class})
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.summary.class})
	private Integer id;

	/**
	 * 用户名
	 */
	@NotNull
	@Pattern(regexp = "^[\\u4e00-\\u9fa5]{3,5}$|^[\\dA-Za-z_]{3,10}$",groups = {ValidInterface.class})//匹配3-5个汉字，或3-10个字节（中文，英文，数字及下划线(_)）
	@Column(name = "username", length = 50)
	@JsonView({View.summary.class})
	private String username;

	/**
	 * 用户电话号码
	 */
	@NotNull
	@Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$",groups = {ValidInterface.class})
	@Column(name = "phone", length = 11 , unique = true)
	@JsonView({View.commentExcept.class})
	private String phone;

	/**
	 * 用户密码
	 */
	@NotNull
	@Pattern(regexp = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{6,32}",groups = {ValidInterface.class})//匹配6-32个字符，可包含中文，英文，数字及下划线(_)
	@Column(name = "password", length = 50)
	@JsonView({View.commentExcept.class })
	private String password;

	/**
	 * 令牌
	 */
	@Column(name = "token", length = 100)
	@JsonView({View.commentExcept.class, View.exceptPwd.class})
	private String token;

	/**
	 * 头像
	 */
	@Column(name = "avatar", length = 80)
	@JsonView({View.commentExcept.class, View.exceptPwd.class})
	private String avatar;

	/**
	 * 创建日期
	 */
	@Column(name = "create_date", length = 19)
	@JsonView({View.commentExcept.class})
	private Timestamp create_date;

	/**
	 * 更新日期
	 */
	@Column(name = "update_date", length = 19)
	@JsonView({View.commentExcept.class})
	private Timestamp update_date;
	// Constructors

	/** default constructor */
	public User() {
	}

	// Property accessors

	public User(int id) {
		this.id = id;
	}

	public User(String phone){
		this.phone = phone;
	}

	public User(String username, String password,String phone) {
		this.username = username;
		this.password = password;
		this.phone = phone;
	}

	public User(Integer id, String username, String password,String phone) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatar() {
		return avatar;
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
		return "User [id=" + id + ", username=" + username + ", phone=" + phone + ", password=" + password + ", token="
				+ token + ", avatar=" + avatar + ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}
}
