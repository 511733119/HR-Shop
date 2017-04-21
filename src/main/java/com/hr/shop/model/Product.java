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
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * @author hjc
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Product implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.summary.class})
	@Min(value = 1 ,groups = {ValidInterface.class})
	private Integer id;

	/**
	 * 商品名称
	 */
	@Column(name = "name", length =20 )
	@JsonView({View.summary.class})
	@NotEmpty
	@Length(min = 1 , max = 20 ,groups = {ValidInterface.class})
	private String name;

	/**
	 * 价格
	 */
	@Column(name = "price", precision=8,scale=2 )
	@JsonView({View.summary.class})
	private BigDecimal price;

	/**
	 * 商品描述
	 */
	@Column(name = "remark", length =50 )
	@JsonView({View.summary.class})
	private String remark;

	/**
	 * 创建日期
	 */
	@Column(name = "create_date", length =19 )
	private Timestamp create_date;

	/**
	 * 更新日期
	 */
	@Column(name = "update_date", length =19 )
	@JsonView({View.summary.class})
	private Timestamp update_date;

	/**
	 * 是否是推荐
	 */
	@Column(name = "commend")
	private Boolean commend;

	/**
	 * 是否可展示
	 */
	@Column(name = "open" )
	private Boolean open;

	/**
	 * 销量
	 */
	@Column(name = "sales" )
	@JsonView({View.summary.class })
	@Min(0)
	private Integer sales;

	/**
	 * 对应商品种类
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="cid")
	private Category category;

	/**
	 * 对应所属商家
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bid")
	@JsonView({View.son2.class })
	private Business business;
	
	/**
	 * 对应商品细分种类
	 */
	@OneToMany(targetEntity=Protype.class , cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pid")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonView({View.son2.class})
	private List<Protype> protypeList;

	// Constructors

	/** default constructor */
	public Product() {
	}

	public Product(int id){
		this.id = id;
	}
	
	/** minimal constructor */
	public Product(List<Protype> protypeList) {
		this.protypeList = protypeList;
	}

	// Property accessors

	public List<Protype> getProtypeList() {
		return protypeList;
	}

	public void setProtypeList(List<Protype> protypeList) {
		this.protypeList = protypeList;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreate_date() {
		return this.create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public Boolean getCommend() {
		return this.commend;
	}

	public void setCommend(Boolean commend) {
		this.commend = commend;
	}

	public Boolean getOpen() {
		return this.open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Integer getSales() {
		return sales;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}
	
	public void setBusiness(Business business) {
		this.business = business;
	}
	
	public Business getBusiness() {
		return business;
	}

}