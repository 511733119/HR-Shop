package com.hr.shop.dao;

import com.hr.shop.model.Cart;

import java.util.List;
/**
 * @author hjc
 * 购物车dao接口
 */
public interface CartDao extends BaseDao<Cart> {

	/**
	 * 获得某个用户的购物车
	 * @param uid 用户id
	 */
	public List<Cart> getCartId(int uid);
	/**
	 * 获得购物车的所有数据
	 * @param uid 用户id
	 */
	public List<Cart> getCart(int uid);
	/**
	 * 删除所选购物车项
	 * @param ids 勾选的购物车项id拼接成的字符串
	 */
	public void deleteByCheck(String ids);

	/**
	 * 检查商品库存是否为0
	 * @param ptid 用户已加入购物车欲购买的商品细分种类id
	 * @param number   每件商品对应下单的数量
	 * @return
	 */
	public int checkInventory(String[] ptid  , String[] number);
}
