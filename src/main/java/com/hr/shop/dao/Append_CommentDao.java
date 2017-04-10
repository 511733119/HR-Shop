package com.hr.shop.dao;

import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Comment;

import java.util.List;

/**
 * 追加评论接口
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/31 20:30
 */
public interface Append_CommentDao extends BaseDao<Append_Comment> {
	
	public int getAppend_Comment(int pid, int uid);
	
}

