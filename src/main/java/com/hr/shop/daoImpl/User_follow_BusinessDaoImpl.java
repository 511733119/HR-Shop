package com.hr.shop.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.hr.shop.dao.User_follow_BusinessDao;
import com.hr.shop.model.User_follow_Business;
/**
 * @author hjc
 * 用户关注店铺dao接口实现类
 */
@Repository("user_follow_BusinessDao")
public class User_follow_BusinessDaoImpl extends BaseDaoImpl<User_follow_Business> implements User_follow_BusinessDao {

	@Override
	public long checkIfFollow(int id, int uid){
		String hql = "SELECT count(*) FROM User_follow_Business ufb"
				+ " WHERE ufb.business.id=:id"
				+ " AND ufb.user.id=:uid";
		return (long)getSession().createQuery(hql)
				.setInteger("id", id)
				.setInteger("uid", uid)
				.list().get(0);
	}
	
	@Override
	public void deleteFollow(int id, int uid){
		String hql = "DELETE FROM User_follow_Business  WHERE business.id=:id AND user.id=:uid";
		getSession().createQuery(hql).setInteger("id", id).setInteger("uid", uid).executeUpdate();
	}
	
}