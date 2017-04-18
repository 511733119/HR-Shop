package com.hr.shop.serviceImpl;

import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Append_Comment_Pic;
import com.hr.shop.model.Comment;
import com.hr.shop.model.Comment_Pic;
import com.hr.shop.model.Product;
import com.hr.shop.model.User;
import com.hr.shop.service.Append_CommentService;
import com.hr.shop.service.CommentService;
import com.hr.shop.util.FileUploadUtil;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

/**
 * 追加评论服务接口实现类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/31 20:48
 */
@Service
public class Append_CommentServiceImpl extends BaseServiceImpl<Append_Comment> implements Append_CommentService{

	@Resource
	private FileUploadUtil fileUploadUtil;
	
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
	
	@Override
	public void saveAppend_Comment(String append_Comment,int pid, int uid,MultipartFile[] file,int comment_id,int sid){
		Set<Append_Comment_Pic> acp_Set = new HashSet<Append_Comment_Pic>(); //追评图片集合
    	Append_Comment_Pic append_Comment_Pic = null; 
    	Append_Comment app_com = setAppend_Comment(append_Comment,acp_Set, pid, uid);//给Append_Comment赋值
	    //判断file数组不能为空并且长度大于0
		if(file != null && file.length > 0){
			//循环获取file数组中得文件
			for(int i = 0;i < file.length;i++){
				MultipartFile f = file[i];
				String file_name = fileUploadUtil.saveCommFiles(f);//保存文件
				append_Comment_Pic = setAppend_Comment_Pic(append_Comment_Pic, file_name, app_com);
				acp_Set.add(append_Comment_Pic);
			}
		}
		
    	save(app_com);//保存追加评论内容
    	int app_com_id = getAppend_Comment(pid, uid).get(0).getId();//获取该记录id
    	commentDao.updateComment(comment_id,app_com_id);//修改flag标识，关联追加评论对象
    	sorderDao.updateComm_flag(sid, 2);//标记已追加评论
	}
}
