package com.hr.shop.dao;

import com.hr.shop.model.Protype;
/**
 * @author hjc
 * 商品细分种类dao接口
 */
public interface ProtypeDao extends BaseDao<Protype> {

	/**
	 * 更新库存
	 * @param protype_id
	 * @param inventory
	 */
	public void updateInventory(int protype_id, int inventory);
}
