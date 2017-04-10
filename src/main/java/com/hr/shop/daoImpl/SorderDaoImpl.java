package com.hr.shop.daoImpl;

import org.springframework.stereotype.Repository;

import com.hr.shop.dao.SorderDao;
import com.hr.shop.dto.Sorder_Comment;
import com.hr.shop.model.Sorder;
/**
 * @author hjc
 * 订单项dao接口实现类
 */
@Repository("sorderDao")
public class SorderDaoImpl extends BaseDaoImpl<Sorder> implements SorderDao {

	@Override
	public Sorder_Comment getSorder(int id){
		String hql = "FROM Sorder s WHERE s.id=:id";
//		String hql = "SELECT new com.hr.shop.dto.Sorder_Comment(s.id,s.product.name) FROM Sorder s WHERE s.id=:id";
		return (Sorder)getSession().createQuery(hql).setInteger("id", id).list();
//		return (Sorder_Comment)getSession().createQuery(hql).setInteger("id", id).uniqueResult();
	}
}
