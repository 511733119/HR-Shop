package com.hr.shop.dao;

import com.hr.shop.model.User_follow_Product;
/**
 * @author hjc
 * 用户收藏商品dao接口
 */
public interface User_follow_ProductDao extends BaseDao<User_follow_Product> {

	/**
	 * 删除用户收藏的商品
	 * @param pid
	 * @param id
	 */
	public void deleteFollow(int pid, int id);
	/**
	 * 检查用户是否已收藏该商品
	 * @param pid
	 * @param id
	 * @return
	 */
	public long checkIfFollow(int pid, int id);
}
