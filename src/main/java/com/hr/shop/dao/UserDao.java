package com.hr.shop.dao;

import java.util.List;

import com.hr.shop.model.User;
import com.hr.shop.model.User_follow_Business;
import com.hr.shop.model.User_follow_Product;
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

	/**
	 * 更新用户名
	 * @param id
	 * @param username
	 */
	public void updateUsername(int id, String username);

	/**
	 * 更新密码
	 * @param id
	 * @param password
	 */
	public void updatePassword(int id, String password);

	/**
	 * 更新头像
	 * @param id
	 * @param avatar
	 */
	public void updatAvatar(int id, String avatar);
	
	/**
	 * 更新token
	 * @param token
	 * @param uid
	 */
	public void updateToken(String token , int uid);
	/**
	 * 获取用户收藏的店铺
	 * @param uid
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<User_follow_Business> getUserFollowsBusiness(int uid , int pageNum , int pageSize);
	/**
	 * 获取用户收藏的商品
	 * @param id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<User_follow_Product> getUserFollowsProduct(int id, int pageNum, int pageSize);
}
