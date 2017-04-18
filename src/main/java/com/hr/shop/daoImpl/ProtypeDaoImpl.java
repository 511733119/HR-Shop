package com.hr.shop.daoImpl;

import org.springframework.stereotype.Repository;

import com.hr.shop.dao.ProtypeDao;
import com.hr.shop.model.Protype;
/**
 * @author hjc
 * 商品细分种类dao接口实现类
 */
@Repository("protypeDao")
public class ProtypeDaoImpl extends BaseDaoImpl<Protype> implements ProtypeDao {

	@Override
	public void updateInventory(int protype_id, int inventory){
		String hql = "UPDATE Protype pt SET pt.inventory=:inventory WHERE pt.id=:protype_id";
		getSession().createQuery(hql)
					.setInteger("inventory", inventory)
					.setInteger("protype_id", protype_id)
					.executeUpdate();
	}
	
}
