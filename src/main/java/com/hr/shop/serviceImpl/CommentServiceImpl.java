package com.hr.shop.serviceImpl;

import com.hr.shop.model.Comment;
import com.hr.shop.model.Comment_Pic;
import com.hr.shop.model.Product;
import com.hr.shop.model.User;
import com.hr.shop.service.CommentService;
import com.hr.shop.util.FileUploadUtil;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

/**
 * 评论服务接口类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/31 20:48
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment> implements CommentService{

	@Resource
	private FileUploadUtil fileUploadUtil;
	
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
	public void saveComment(int star, String comment , MultipartFile[] file , int pid, int uid , int sid){
		Comment comm = new Comment(star, comment);//初始化comment对象
    	Set<Comment_Pic> cp_Set = new HashSet<Comment_Pic>(); 
    	Comment_Pic comment_Pic = null;
    	//判断file数组不能为空并且长度大于0
		if(file != null && file.length > 0){
			//循环获取file数组中得文件
			for(int i = 0;i < file.length;i++){
				MultipartFile f = file[i];
				String file_name = fileUploadUtil.saveCommFiles(f);//保存文件
				comment_Pic = setComment_Pic(comment_Pic, file_name, comm);
				cp_Set.add(comment_Pic);
			}
		}
		comm = set_Comment_val(comm, cp_Set, pid, uid);//给Comment对象赋值
		save(comm);//保存评论
		sorderDao.updateComm_flag(sid, 1);//标记已评论
	}
}
