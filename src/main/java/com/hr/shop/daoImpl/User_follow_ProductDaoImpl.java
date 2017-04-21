package com.hr.shop.daoImpl;


import org.springframework.stereotype.Repository;
import com.hr.shop.dao.User_follow_ProductDao;
import com.hr.shop.model.User_follow_Product;
/**
 * @author hjc
 * 用户关注店铺dao接口实现类
 */
@Repository("user_follow_ProductDao")
public class User_follow_ProductDaoImpl extends BaseDaoImpl<User_follow_Product> implements User_follow_ProductDao {

	@Override
	public void deleteFollow(int pid, int id) {
		String hql ="DELETE FROM User_follow_Product ufp"
				+ " WHERE ufp.user.id=:id"
				+ " AND ufp.product.id=:pid";
		getSession().createQuery(hql)
				.setInteger("id", id)
				.setInteger("pid", pid)
				.executeUpdate();
	}

	@Override
	public long checkIfFollow(int pid, int id){
		String hql = "SELECT count(*) FROM User_follow_Product ufp"
				+ " WHERE ufp.user.id=:id"
				+ " AND ufp.product.id=:pid";
		return (long)getSession().createQuery(hql)
					.setInteger("id", id)
					.setInteger("pid",pid)
					.list().get(0);
	}
}