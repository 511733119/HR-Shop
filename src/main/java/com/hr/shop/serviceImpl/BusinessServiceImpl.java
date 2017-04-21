package com.hr.shop.serviceImpl;

import com.hr.shop.model.Business;
import com.hr.shop.model.Product;
import com.hr.shop.service.BusinessService;

import java.util.List;

import org.springframework.stereotype.Service;


/**
 * 商家服务接口实现类
 * @author hjc
 *
 */
@Service("businessService")
public class BusinessServiceImpl extends BaseServiceImpl<Business> implements BusinessService {

	@Override
	public List<Product> getInto_Business(int id , int pageNum , int pageSize) {
		return businessDao.getInto_Business(id , pageNum , pageSize);
	}

	@Override
	public List<Product> getNew_Product(int id, int pageNum, int pageSize) {
		return businessDao.getNew_Product(id, pageNum, pageSize);
	}

	
}
