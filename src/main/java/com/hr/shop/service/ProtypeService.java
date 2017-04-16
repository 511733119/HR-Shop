package com.hr.shop.service;

import com.hr.shop.model.Protype;

public interface ProtypeService extends BaseService<Protype> {

	/**
	 * 更新库存
	 * @param protype_id
	 * @param inventory
	 */
	public void updateInventory(int protype_id, int inventory);
}
