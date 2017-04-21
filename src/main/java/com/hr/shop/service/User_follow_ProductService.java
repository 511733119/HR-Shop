package com.hr.shop.service;

import com.hr.shop.model.User_follow_Product;
/**
 * 用户收藏商品服务接口类
 * @author hjc
 *
 */
public interface User_follow_ProductService extends BaseService<User_follow_Product> {
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
