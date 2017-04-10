package com.hr.shop.serviceImpl;

import com.hr.shop.model.Product;
import com.hr.shop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 模块自身的业务逻辑
 */
@SuppressWarnings("unchecked")
@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

	@Override
	public List<Product> queryJoinCategory(String name, int page, int size) {
		return productDao.queryJoinCategory(name, page, size);                            
	}
	
	@Override
	public Long getCount(String name) {
		return productDao.getCount(name);
	}

	@Override
	public List<Product> queryByCid(int cid) {
		return productDao.queryByCid(cid);
	}


	@Override
	public List<Product> getSimilarProduct(String name ,int pageNum,int pageSize) {
		return productDao.getSimilarProduct(name , pageNum , pageSize);
	}
	
	@Override
	public List<Product> findProduct( int pageNum,int pageSize,int id) {
		return productDao.findProduct(pageNum, pageSize, id);
	}

	@Override
	public List<Product> findNewest_Or_HighestSalesProduct(int pageNum, int pageSize, String field) {
		return productDao.findNewest_Or_HighestSalesProduct(pageNum, pageSize, field);
	}

	@Override
	public List<Product> getProduct(int pid){
		return productDao.getProduct(pid);
	}

	@Override
	public List<Product> getSearchList() {
		return productDao.getSearchList();
	}
}
