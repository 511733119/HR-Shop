package com.hr.shop.daoImpl;

import org.springframework.stereotype.Repository;

import com.hr.shop.dao.SorderDao;
import com.hr.shop.model.Sorder;
/**
 * @author hjc
 * 订单项dao接口实现类
 */
@Repository("sorderDao")
public class SorderDaoImpl extends BaseDaoImpl<Sorder> implements SorderDao {

	@Override
	public void updateComm_flag(int id , int comm_flag){
		String hql = "UPDATE Sorder s SET s.comm_flag=:comm_flag WHERE s.id=:id";
		getSession().createQuery(hql)
					.setInteger("comm_flag", comm_flag)
					.setInteger("id", id)
					.executeUpdate();
	}
}
