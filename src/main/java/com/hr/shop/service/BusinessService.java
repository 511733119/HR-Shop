package com.hr.shop.service;

import com.hr.shop.model.Business;
import com.hr.shop.model.Product;

import java.util.List;
/**
 * 商家服务接口类
 * @author hjc
 *
 */
public interface BusinessService extends BaseService<Business> {
	/**
	 * 店铺首页展示数据
	 * @param id
	 * @param pageNum
	 * @return
	 */
	public List<Product> getInto_Business(int id , int pageNum , int pageSize);
	
	public List<Product> getNew_Product(int id, int pageNum , int pageSize);
}
