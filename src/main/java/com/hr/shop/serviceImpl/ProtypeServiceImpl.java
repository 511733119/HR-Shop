package com.hr.shop.serviceImpl;

import org.springframework.stereotype.Service;

import com.hr.shop.model.Protype;
import com.hr.shop.service.ProtypeService;

/*
 * 模块自身的业务逻辑
 */
@Service("protypeService")
public class ProtypeServiceImpl extends BaseServiceImpl<Protype> implements ProtypeService {

	@Override
	public void updateInventory(int protype_id, int inventory) {
		protypeDao.updateInventory(protype_id, inventory);
	}

}
