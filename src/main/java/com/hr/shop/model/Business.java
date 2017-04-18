package com.hr.shop.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;
import com.hr.shop.validatorInterface.ValidInterface;
/**
 * 商家类
 * @author hjc
 */
@Entity
@Table(name = "business")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Business implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商家id
	 */
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Min(value = 1 ,groups = {ValidInterface.class})
	@JsonView({View.summary.class})
	private int id;	
	
	/**
	 * 商家名称
	 */
	@Column(name = "name", length =20 )
	@JsonView({View.summary.class})
	@NotEmpty
	@Length(min = 1 , max = 20 ,groups = {ValidInterface.class})
	private String name; 
	
	/**
	 * 关注数
	 */
	@Column(name = "followers" )
	@JsonView({View.summary.class })
	private int followers;  

	/**
	 * 收藏数
	 */
	@Column(name = "collectors" )
	@JsonView({View.summary.class })
	private int collectors;	
	
	/**
	 * 商家头像
	 */
	@Column(name = "pic", length =100 )
	@JsonView({View.summary.class})
	private String pic;  
	
	/**
     * 追加评论中的图片集合
     */
    @OneToMany(targetEntity=Product.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bid")
	private Set<Product> productSet; //商家所售商品
    
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
	
	public Business(){}

	public Business(int id, String name, int followers, int collectors, String pic) {
		super();
		this.id = id;
		this.name = name;
		this.followers = followers;
		this.collectors = collectors;
		this.pic = pic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public int getCollectors() {
		return collectors;
	}

	public void setCollectors(int collectors) {
		this.collectors = collectors;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}
	
	public Set<Product> getProductSet() {
		return productSet;
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
		return "Business [id=" + id + ", name=" + name + ", followers=" + followers + ", collectors=" + collectors
				+ ", pic=" + pic + ", productSet=" + productSet + ", create_date=" + create_date + ", update_date="
				+ update_date + "]";
	}
	
}
