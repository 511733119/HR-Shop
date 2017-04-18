package com.hr.shop.service;

import com.hr.shop.dto.Comment_dto;
import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Append_Comment_Pic;
import com.hr.shop.model.Comment;
import com.hr.shop.model.Comment_Pic;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

/**
 * 评论服务接口
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/31 20:47
 */
public interface CommentService extends BaseService<Comment> {

	/**
	 * 获取商品评论
	 * @param pid
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<Comment> getProductComment(int pid , int pageNum , int pageSize);

	/**
	 * 更新flag, 关联追加评论对象
	 * @param comment_id 评论id
	 * @param app_com_id 追加评论对象id
	 */
	 public void updateComment(int comment_id,int app_com_id);
	 /**
     * 追加评论时界面显示上一次的评论内容
     * @param pid
     * @param uid
     * @return
     */
	 public List<Comment> getComment(int pid, int uid);
	 
	 /**
	  * 给Comment_Pic对象赋值
	  * @param comment_Pic
	  * @param file_name
	  * @param comm
	  * @return
	  */
	 public Comment_Pic setComment_Pic(Comment_Pic comment_Pic , String file_name , Comment comm);

	 /**
	  * 给Comment对象赋值
	  * @param comm
	  * @param cp_Set
	  * @param pid
	  * @param uid
	  * @return
	  */
	 public Comment set_Comment_val(Comment comm , Set<Comment_Pic> cp_Set , int pid , int uid);
	 
	 /**
	  * 保存评论
	  * @param star 星星数
	  * @param comment 评论
	  * @param file 
	  * @param pid 商品id
	  * @param uid 用户id
	  * @param sid 订单项id
	  */
	 public void saveComment(int star, String comment , MultipartFile[] file , int pid, int uid , int sid);
	 
}
