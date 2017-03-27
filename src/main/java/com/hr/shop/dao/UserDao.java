package com.hr.shop.dao;

import com.hr.shop.model.User;
/**
 * @author hjc
 * 用户dao接口
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * 账号密码登录
	 * @param user
	 */
	public User login(User user);
	/**
	 * 检查token是否存在
	 * @param id 用户id
	 * @param token 用户登录的令牌
	 */
	public User checkToken(int id, String token);
	/**
	 * 获取用户
	 * @param phone 用户电话号码
	 */
	public User getUser(String phone);

	/**
	 * 获得所有用户名
	 * @param name
	 * @return
	 */
	public User getAllUserName(String name);
}