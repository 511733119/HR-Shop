package com.hr.shop.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hr.shop.dao.CartDao;
import com.hr.shop.model.Cart;

/**
 * @author hjc
 * 购物车dao接口实现类
 */
@Repository("cartDao")
public class CartDaoImpl extends BaseDaoImpl<Cart> implements CartDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Cart> getCartId(int uid) {
		String hql = "FROM Cart WHERE uid=:uid";
		return getSession().createQuery(hql)
			.setInteger("uid", uid)
			.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cart> getCart(int uid) {
		String hql = " SELECT DISTINCT c FROM Cart c JOIN FETCH c.protype pt JOIN FETCH pt.product p JOIN FETCH p.protypeSet JOIN FETCH p.category WHERE c.user.id=:uid";
		return getSession().createQuery(hql)
					.setCacheable(true)
					.setInteger("uid", uid)
					.list();
	}

	@Override
	public void deleteByCheck(String ids) {
		String hql = "DELETE FROM Cart WHERE id in("+ids+")";
		getSession().createQuery(hql)
				.executeUpdate();
	}

	@Override
	public int checkInventory(String[] ptid , String[] number) {
		//进行数据库查询字符串拼接，使用StringBuilder
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT count(*) FROM Protype pt WHERE pt.inventory>="+ number[0]+" And id=").append(ptid[0]);
		int len = ptid.length;
		if(len > 1){
			for (int i = 1; i < len; i++){
				stringBuilder.append(" OR pt.inventory>="+number[i]+"AND id=" + ptid[i]);
			}
		}
		String hql = stringBuilder.toString();
		return ((Number)getSession().createQuery(hql)
							.uniqueResult())
							.intValue();
	}

	@Override
	public void updateCartNumber(int id, int number) {
		String hql ="UPDATE Cart c SET c.number = :number WHERE c.id = :id";
		getSession().createQuery(hql).setInteger("number",number).setInteger("id",id).executeUpdate();
	}
}
