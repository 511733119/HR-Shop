package com.hr.shop.dao;

import java.util.List;

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
	
	/**
	 * 用户支付成功后获取所购买商品的id和购买的数量
	 * @param fid
	 * @return
	 */
	public List<Object[]> getSorderList(String fid);
}
