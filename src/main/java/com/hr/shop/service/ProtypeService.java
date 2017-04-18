package com.hr.shop.service;

import com.hr.shop.model.Protype;
/**
 * 商品细分种类服务接口类
 * @author hjc
 *
 */
public interface ProtypeService extends BaseService<Protype> {

	/**
	 * 更新库存
	 * @param protype_id
	 * @param inventory
	 */
	public void updateInventory(int protype_id, int inventory);
}
