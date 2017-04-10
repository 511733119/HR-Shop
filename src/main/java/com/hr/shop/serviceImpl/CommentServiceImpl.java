package com.hr.shop.serviceImpl;

import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Comment;
import com.hr.shop.model.Comment_Pic;
import com.hr.shop.model.Product;
import com.hr.shop.model.User;
import com.hr.shop.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 评论服务接口类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/31 20:48
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService{

	@Override
	public List<Comment> getProductComment(int pid, int pageNum, int pageSize) {
		 return commentDao.getProductComment(pid,pageNum,10);
	}

	@Override
	public void updateComment(int comment_id,int app_com_id) {
		commentDao.updateComment(comment_id,app_com_id);
	}
	
	@Override
	public List<Comment> getComment(int pid, int uid){
		return commentDao.getComment(pid, uid);
	}
	
	@Override
	public Comment_Pic setComment_Pic(Comment_Pic comment_Pic , String file_name , Comment comm){
		comment_Pic = new Comment_Pic();
		comment_Pic.setPic(file_name);
		comment_Pic.setComment(comm);
		return comment_Pic;
	}
	
	@Override
	public Comment set_Comment_val(Comment comm , Set<Comment_Pic> cp_Set , int pid , int uid){
		comm.setComment_pic_Set(cp_Set);//级联评论图片对象
    	comm.setProduct(new Product(pid));//关联商品对象
    	comm.setUser(new User(uid));//关联用户对象
    	return comm;
	}
	
	@Override
	public Append_Comment setAppend_Comment(String append_Comment , int pid, int uid){
		Append_Comment app_com = new Append_Comment(append_Comment); 
    	app_com.setProduct(new Product(pid));
    	app_com.setUser(new User(uid));
    	return app_com;
	}
}
