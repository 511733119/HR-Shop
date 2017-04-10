package com.hr.shop.dao;

import com.hr.shop.dto.Sorder_Comment;
import com.hr.shop.model.Sorder;
/**
 * @author hjc
 * 订单项dao接口
 */
public interface SorderDao extends BaseDao<Sorder> {

	/**
	 * 获取用户点击评论时显示的所买商品的信息
	 * @param id
	 * @return
	 */
	public Sorder_Comment getSorder(int id);
}
