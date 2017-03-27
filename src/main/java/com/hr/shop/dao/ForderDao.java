package com.hr.shop.dao;

import com.hr.shop.model.Forder;

import java.util.List;
/**
 * @author hjc
 * 订单dao接口
 */
public interface ForderDao extends BaseDao<Forder> {

	/**
	 * 根据订单编号，更新订单状态
	 * @param id 订单id
	 * @param sid 状态id
	 */
	public void updateStatusById(int id, int sid);
	/**
	 * 查询用户对应的全部订单
	 * @param uid 用户id
	 * @param pageNum 页码
	 * @param pageSize 每页显示pagesize条数据
	 * @return
	 */
	public List<Forder> findAllOrder(int uid, int pageNum, int pageSize);

	/**
	 * 删除订单
	 * @param id  订单id
	 * @return
	 */
	public int deleteOrder(String id);

	/**
	 * 用户未付款，则显示取消订单，设置订单状态为交易关闭，恢复库存
	 * @param id  订单id
	 * @return
	 */
	public int cancelOrder(String id);

	/**
	 * 根据订单号查找订单
	 * @param id
	 * @return
	 */
	public Forder getOrder(String id);


}
