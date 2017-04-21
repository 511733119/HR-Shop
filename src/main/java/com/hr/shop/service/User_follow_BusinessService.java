package com.hr.shop.service;

import java.util.List;

import com.hr.shop.model.User_follow_Business;
/**
 * 用户关注店铺服务接口类
 * @author hjc
 *
 */
public interface User_follow_BusinessService extends BaseService<User_follow_Business> {
	/**
	 * 检查用户是否已收藏该店铺
	 * @param id
	 * @param uid
	 * @return
	 */
	public long checkIfFollow(int id, int uid);
	/**
	 * 删除用户收藏该店铺的记录
	 * @param id
	 * @param uid
	 */
	public void deleteFollow(int id, int uid);
	
}
