package com.hr.shop.dao;

import com.hr.shop.model.Sorder;
/**
 * @author hjc
 * 订单项dao接口
 */
public interface SorderDao extends BaseDao<Sorder> {

	/**
	 * 更新评论标志
	 * @param id
	 * @param comm_flag
	 */
	public void updateComm_flag(int id , int comm_flag);
}
