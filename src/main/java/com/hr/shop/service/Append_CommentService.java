package com.hr.shop.service;

import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Append_Comment_Pic;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

/**
 * 追加评论服务接口类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/31 20:47
 */
public interface Append_CommentService extends BaseService<Append_Comment> {
	/**
	 * 获取某用户对某商品的追加评论id
	 * @param pid
	 * @param uid
	 * @return
	 */
	public List<Append_Comment> getAppend_Comment(int pid, int uid);
	
	 /**
	  * 给Append_Comment对象赋值
	  * @param append_Comment
	  * @param acp_Set
	  * @param pid
	  * @param uid
	  * @return
	  */
	 public Append_Comment setAppend_Comment(String append_Comment,List<Append_Comment_Pic> acp_List , int pid, int uid);
	 
	/**
	 * 给Append_Comment_Pic对象赋值
	 * @param append_Comment_Pic
	 * @param file_name
	 * @param app_com
	 * @return
	 */
	public Append_Comment_Pic setAppend_Comment_Pic(Append_Comment_Pic append_Comment_Pic , String file_name , Append_Comment app_com);

	/**
	 * 保存追评
	 * @param append_Comment 追评
	 * @param pid 商品id
	 * @param uid 用户id
	 * @param file 
	 * @param comment_id 评论id
	 * @param sid 订单项id
	 */
	public void saveAppend_Comment(String append_Comment,int pid, int uid,MultipartFile[] file,int comment_id,int sid);
}
