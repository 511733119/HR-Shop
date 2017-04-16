package com.hr.shop.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.jsonView.View;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 评论实体类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/30 15:29
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(value = true)
public class Comment implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 评论id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.summary.class})
    private int id;
    /**
     * 评论星星数
     */
    @Column(name = "star" )
    @JsonView({View.summary.class})
    private int star;
    /**
     * 评论内容
     */
    @Column(name = "comment", length = 100)
    @JsonView({View.summary.class})
    private String comment;
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
    @JsonView({View.summary.class})
    private Timestamp update_date;
    /**
     * 是否已追加评论标志位
     */
    @Column(name = "flag", length = 1)
    @JsonView({View.summary.class})
    private int flag ;

    /**
     * 关联用户
     */
    @ManyToOne()
    @JoinColumn(name="uid")
    @JsonView({View.summary.class})
    private User user;

    /**
     * 关联商品
     */
    @ManyToOne()
    @JoinColumn(name="pid")
    @JsonView({View.summary.class})
    private Product product;

    /**
     * 追加评论中的图片集合
     */
    @OneToMany(targetEntity=Comment_Pic.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id")
    @JsonView({View.summary.class})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Comment_Pic> comment_pic_Set;

    @OneToOne()
    @JoinColumn(name="append_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView({View.summary.class})
    private Append_Comment append_comment;

    public Comment(){}
    
    public Comment(int star, String comment) {
		this.star = star;
		this.comment = comment;
	}

	public Comment(int star, String comment , int flag){
        this.star = star;
        this.comment = comment;
        this.flag = flag;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getStar() {
        return star;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
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

    public void setComment_pic_Set(Set<Comment_Pic> comment_pic_Set) {
        this.comment_pic_Set = comment_pic_Set;
    }

    public Set<Comment_Pic> getComment_pic_Set() {
        return comment_pic_Set;
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
    
    public void setAppend_comment(Append_Comment append_comment) {
		this.append_comment = append_comment;
	}
    public Append_Comment getAppend_comment() {
		return append_comment;
	}
}
