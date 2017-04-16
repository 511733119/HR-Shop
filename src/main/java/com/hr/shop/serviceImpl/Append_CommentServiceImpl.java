package com.hr.shop.serviceImpl;

import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Append_Comment_Pic;
import com.hr.shop.model.Comment;
import com.hr.shop.model.Comment_Pic;
import com.hr.shop.model.Product;
import com.hr.shop.model.User;
import com.hr.shop.service.Append_CommentService;
import com.hr.shop.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 追加评论服务接口实现类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/31 20:48
 */
@Service
public class Append_CommentServiceImpl extends BaseServiceImpl<Append_Comment> implements Append_CommentService{

	@Override
	public List<Append_Comment> getAppend_Comment(int pid, int uid) {
		return append_CommentDao.getAppend_Comment(pid, uid);
	}
	
	@Override
	public Append_Comment_Pic setAppend_Comment_Pic(Append_Comment_Pic append_Comment_Pic , String file_name , Append_Comment app_com){
		append_Comment_Pic = new Append_Comment_Pic();
		append_Comment_Pic.setPic(file_name);
		append_Comment_Pic.setAppend_comment(app_com);
		return append_Comment_Pic;
	}
	
	@Override
	public Append_Comment setAppend_Comment(String append_Comment ,Set<Append_Comment_Pic> acp_Set, int pid, int uid){
		Append_Comment app_com = new Append_Comment(append_Comment); 
		app_com.setAppend_comment_pic_Set(acp_Set);
    	app_com.setProduct(new Product(pid));
    	app_com.setUser(new User(uid));
    	return app_com;
	}
}
