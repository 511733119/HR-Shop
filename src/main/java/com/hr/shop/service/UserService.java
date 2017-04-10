package com.hr.shop.service;

import com.hr.shop.model.User;

public interface UserService extends BaseService<User> {

	/**
	 * 账号密码登录
	 * @param user
	 */
	public User login(User user);
	/**
	 *用户注册发送短信验证码
	 * @param phone 电话号码
	 */
	public String sendCode(String phone);
	/**
	 *随机生成6位数字字符数组
	 */
    public String generateCheckPass();
	/**
	 *注册时随机生成用户名
	 */
    public String generateUsername();
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
	 * 判断是否是手机号码
	 * @param mobiles 电话号码
	 */
	public boolean isMobileNumber(String mobiles);
	/**
	 * 用户成功登录或注册时生成token
	 * @param username
	 * @param password
	 */
	public String generateToken(String username, String password);

	/**
	 * 获得所有用户名
	 * @param name
	 * @return
	 */
	public boolean getAllUserName(String name);

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
}
