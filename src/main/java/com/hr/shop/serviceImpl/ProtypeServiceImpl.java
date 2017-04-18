package com.hr.shop.serviceImpl;

import org.springframework.stereotype.Service;

import com.hr.shop.model.Protype;
import com.hr.shop.service.ProtypeService;

/**
 * 商品细分种类服务接口实现类
 * @author hjc
 *
 */
@Service("protypeService")
public class ProtypeServiceImpl extends BaseServiceImpl<Protype> implements ProtypeService {

	@Override
	public void updateInventory(int protype_id, int inventory) {
		protypeDao.updateInventory(protype_id, inventory);
	}

}
