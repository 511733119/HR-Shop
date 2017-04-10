package com.hr.shop.service;

import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Comment;

import java.util.List;

/**
 * 追加评论服务接口类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/31 20:47
 */
public interface Append_CommentService extends BaseService<Append_Comment> {

	public int getAppend_Comment(int pid, int uid);
}
