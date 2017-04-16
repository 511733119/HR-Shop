package com.hr.shop.daoImpl;

import com.hr.shop.dao.ProductDao;
import com.hr.shop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author hjc
 * 商品dao接口实现类
 */
@SuppressWarnings("unchecked")
@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {

	@Override
	public List<Product> queryJoinCategory(String name, int page, int size) {
		String hql = "FROM Product p LEFT JOIN FETCH p.category WHERE p.name LIKE :name";
		return getSession().createQuery(hql)
			.setString("name", "%"+ name +"%")
			.setFirstResult((page-1)*size)
			.setMaxResults(size)
			.list();
	}
	
	@Override
	public Long getCount(String name) {
		String hql = "SELECT count(p) FROM Product p WHERE p.name LIKE :name";
		return (Long) getSession().createQuery(hql)
			.setString("name", "%"+ name +"%")
			.uniqueResult();
	}

	@Override
	public List<Product> queryByCid(int cid) {
		String hql = "FROM Product p"
				+ " JOIN FETCH p.category"
				+ " WHERE p.commend=true"
				+ " AND p.open=true"
				+ " AND p.category.id=:cid"
				+ " ORDER BY p.create_date DESC";
		return getSession().createQuery(hql).setInteger("cid", cid)
				.setFirstResult(0)
				.setMaxResults(4)
				.list();
	}
	
	@Override
	public List<Product> getSimilarProduct(String name ,int pageNum,int pageSize) {
		int fromIndex = pageSize * (pageNum-1);

		String hql = "FROM Product p"
				+ " JOIN FETCH p.protypeSet"
				+ " JOIN FETCH p.category"
				+ " WHERE p.commend=true"
				+ " AND p.open=true"
				+ " AND p.name LIKE :name"
				+ " ORDER BY p.sales DESC";
		return getSession().createQuery(hql)
				.setString("name", "%"+name+"%")
				.setFirstResult(fromIndex)
				.setMaxResults(pageSize)
				.list();
	}

	@Override
	public List<Product> findProduct( int pageNum,int pageSize,int id) {
		
		int fromIndex = pageSize * (pageNum-1);
		
		String hql ="FROM Product p"
				+ " JOIN FETCH p.protypeSet"
				+ " JOIN FETCH p.category"
				+ " WHERE p.commend=true"
				+ " AND p.open=true"
				+ " AND p.category.id=:cid"
				+ " ORDER BY p.sales DESC";
		return getSession().createQuery(hql)
						.setInteger("cid", id)
						.setFirstResult(fromIndex)
						.setMaxResults(pageSize)
						.setCacheable(true)
						.list();
	}

	//查询结果进行缓存
	@Override
	public List<Product> findNewest_Or_HighestSalesProduct(int pageNum, int pageSize, String field) {

		int fromIndex = pageSize * (pageNum-1);

		String hql ="FROM Product p"
				+ " JOIN FETCH p.protypeSet"
				+ " JOIN fetch p.category"
				+ " WHERE p.commend=true"
				+ " AND p.open=true"
				+ " ORDER BY p."+ field +" DESC";
		return getSession().createQuery(hql)
						.setCacheable(true)
						.setFirstResult(fromIndex)
						.setMaxResults(pageSize)
						.list();
	}

	@Override
	public List<Product> getProduct(int pid) {
		String hql = "FROM Product p"
				+ " JOIN FETCH p.category"
				+ " JOIN FETCH p.protypeSet"
				+ " WHERE p.id=:pid";
		return  getSession().createQuery(hql)
				.setInteger("pid", pid)
				.setFirstResult(0)
				.setMaxResults(1)
				.setCacheable(true)
				.list();
	}

	@Override
	public List<Product> getSearchList() {
		String hql = "SELECT p.name FROM Product p"
				+ " WHERE p.commend=true"
				+ " AND p.open=true"
				+ " ORDER BY p.sales desc ";
		return getSession().createQuery(hql)
				.setFirstResult(0)
				.setMaxResults(10)
				.setCacheable(true)
				.list();
	}
}
