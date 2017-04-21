package com.hr.shop.daoImpl;

import com.hr.shop.dao.BusinessDao;
import com.hr.shop.dao.ProductDao;
import com.hr.shop.model.Business;
import com.hr.shop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author hjc
 * 商家dao接口实现类
 */
@Repository("businessDao")
public class BusinessDaoImpl extends BaseDaoImpl<Business> implements BusinessDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getInto_Business(int id , int pageNum , int pageSize){
		int fromIndex = pageSize * (pageNum-1);
		String hql = "FROM Product p"
				+ " JOIN FETCH p.business b"
				+ " JOIN FETCH p.protypeList"
				+ " JOIN FETCH p.category"
				+ " WHERE b.id=:id"
				+ " AND p.commend=true"
				+ " AND p.open=true"
				+ " ORDER BY p.sales DESC"
				+ " ,p.update_date DESC";
		return getSession().createQuery(hql)
				.setInteger("id", id)
				.setFirstResult(fromIndex)
				.setMaxResults(pageSize)
				.list();
	}
	
	@Override
	public List<Product> getNew_Product(int id, int pageNum , int pageSize){
		int fromIndex = pageSize * (pageNum-1);
		String hql = "FROM Product p"
				+ " JOIN FETCH p.business b"
				+ " JOIN FETCH p.protypeList"
				+ " JOIN FETCH p.category"
				+ " WHERE b.id=:id"
				+ " AND p.commend=true"
				+ " AND p.open=true"
				+ " ORDER BY p.update_date DESC";
		return getSession().createQuery(hql)
				.setInteger("id", id)
				.setFirstResult(fromIndex)
				.setMaxResults(pageSize)
				.list();
	}
	
}
