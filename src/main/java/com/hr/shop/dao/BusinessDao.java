package com.hr.shop.dao;

import com.hr.shop.model.Business;
import com.hr.shop.model.Product;

import java.util.List;
/**
 * @author hjc
 * 商家dao接口
 */
public interface BusinessDao extends BaseDao<Business> {
	/**
	 * 店铺首页展示数据
	 * @param id
	 * @param pageNum
	 * @return
	 */
	public List<Product> getInto_Business(int id , int pageNum , int pageSize);
	
	public List<Product> getNew_Product(int id, int pageNum , int pageSize);
}
