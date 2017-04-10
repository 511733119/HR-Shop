package com.hr.shop.service;

import java.util.List;

import com.hr.shop.dto.Sorder_Comment;
import com.hr.shop.model.Sorder;

public interface SorderService extends BaseService<Sorder> {

	/**
	 * 获取用户点击评论时显示的所买商品的信息
	 * @param id
	 * @return
	 */
	public Sorder_Comment getSorder(int id);
}
