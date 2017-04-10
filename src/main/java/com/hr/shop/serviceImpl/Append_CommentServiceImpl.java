package com.hr.shop.serviceImpl;

import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Comment;
import com.hr.shop.service.Append_CommentService;
import com.hr.shop.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 追加评论服务接口实现类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/31 20:48
 */
@Service
public class Append_CommentServiceImpl extends BaseServiceImpl<Append_Comment> implements Append_CommentService{

	@Override
	public int getAppend_Comment(int pid, int uid) {
		return append_CommentDao.getAppend_Comment(pid, uid);
	}
}
