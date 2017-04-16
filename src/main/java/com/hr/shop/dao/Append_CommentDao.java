package com.hr.shop.dao;

import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Comment;

import java.util.List;

/**
 * 追加评论接口
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/31 20:30
 */
public interface Append_CommentDao extends BaseDao<Append_Comment> {
	
	/**
	 * 获取某用户对某商品的追加评论id
	 * @param pid
	 * @param uid
	 * @return
	 */
	public List<Append_Comment> getAppend_Comment(int pid, int uid);
	
}

