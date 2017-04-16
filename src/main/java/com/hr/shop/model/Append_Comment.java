package com.hr.shop.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 追加评论实体类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/30 15:29
 */
@Entity
@Table(name = "append_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Append_Comment implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 追加评论id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.summary.class})
    private int id;
    /**
     * 追加评论内容
     */
    @Column(name = "append_comment" , length = 100)
    @JsonView({View.summary.class})
    private String append_comment;
    
    /**
     * 追加评论对应商品
     */
    @ManyToOne
    @JoinColumn(name="pid")
    private Product product;
    
    /**
     * 追加评论对应用户
     */
    @ManyToOne
    @JoinColumn(name="uid")
    private User user;
    
    /**
     * 创建时间
     */
    @Column(name = "create_date", length = 19)
    @JsonView({View.summary.class})
    private Timestamp create_date;
    /**
     * 更新时间
     */
    @Column(name = "update_date", length = 19)
    private Timestamp update_date;

    /**
     * 追加评论中的图片集合
     */
    @OneToMany(targetEntity=Append_Comment_Pic.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "append_comment_id")
    @JsonView({View.son.class})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Append_Comment_Pic> append_comment_pic_Set;

    public Append_Comment(){}

    public Append_Comment(int id) {
		this.id = id;
	}

	public Append_Comment( String append_comment){
        this.append_comment = append_comment;
    }

    public void setAppend_comment(String append_comment) {
        this.append_comment = append_comment;
    }

    public String getAppend_comment() {
        return append_comment;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setProduct(Product product) {
		this.product = product;
	}
    
    public Product getProduct() {
		return product;
	}
    
    public void setUser(User user) {
		this.user = user;
	}
    
    public User getUser() {
		return user;
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

    public void setAppend_comment_pic_Set(Set<Append_Comment_Pic> append_comment_pic_Set) {
        this.append_comment_pic_Set = append_comment_pic_Set;
    }

    public Set<Append_Comment_Pic> getAppend_comment_pic_Set() {
        return append_comment_pic_Set;
    }
}
