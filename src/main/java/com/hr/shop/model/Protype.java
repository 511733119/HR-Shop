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
 * 商品细分种类类
 */
@Entity
@Table(name = "protype")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Protype implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.summary.class})
	@Min(value = 1,groups = {ValidInterface.class})
	private Integer id;

	/**
	 * 商品细分名
	 */
	@Column(name = "name", length =50 )
	@JsonView({View.summary.class})
	private String name;

	/**
	 * 图片地址
	 */
	@JsonView({View.summary.class})
	@Column(name = "pic", length =200 )
	private String pic;

	/**
	 * 库存
	 */
	@JsonView({View.summary.class})
	@Column(name = "inventory")
	@Min(0)
	private Integer inventory;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="pid")
	@JsonView({View.son.class})
	private Product product;

	/**
	 * 创建日期
	 */
	@Column(name = "create_date", length =19 )
	private Timestamp create_date;

	/**
	 * 更新日期
	 */
	@Column(name = "update_date", length =19 )
	private Timestamp update_date;
	// Constructors

	/** default constructor */
	public Protype() {
	}

	public Protype(Integer id){
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	@Override
	public String toString() {
		return "Protype [id=" + id + ", name=" + name + ", pic=" + pic + ", inventory=" + inventory + ", product="
				+ product + ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}
	
}