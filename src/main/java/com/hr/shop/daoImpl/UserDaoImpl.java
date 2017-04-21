package com.hr.shop.daoImpl;
import com.hr.shop.dao.UserDao;
import com.hr.shop.model.User;
import com.hr.shop.model.User_follow_Business;
import com.hr.shop.model.User_follow_Product;

import java.util.List;

import org.springframework.stereotype.Repository;
/**
 * @author hjc
 * 用户类dao接口实现类
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User getAllUserName(String name) {
		String hql = "FROM User WHERE username=:name";
		return (User)getSession().createQuery(hql)
				.setString("name",name)
				.uniqueResult();
	}

	@Override
	public void updateUsername(int id, String username) {
		String hql = "UPDATE User u SET u.username = :username WHERE u.id=:id";
		getSession().createQuery(hql).setInteger("id",id).setString("username",username).executeUpdate();
	}

	@Override
	public void updatePassword(int id, String password) {
		String hql = "UPDATE User u SET u.password = :password WHERE u.id=:id";
		getSession().createQuery(hql).setInteger("id",id).setString("password",password).executeUpdate();
	}

	@Override
	public void updatAvatar(int id, String avatar) {
		String hql = "UPDATE User u SET u.avatar=:avatar WHERE u.id=:id";
		getSession().createQuery(hql).setInteger("id",id).setString("avatar",avatar).executeUpdate();
	}

	@Override
	public User login(User user) {
		String usernameHql = "FROM User u WHERE u.username=:username AND u.password=:password";
		String phoneHql = "FROM User u WHERE u.phone=:phone AND u.password=:password";
		//如果用户输入的是电话号码
		if(user.getPhone() != null){
			return (User)getSession().createQuery(phoneHql)
					.setString("phone",user.getPhone())
					.setString("password",user.getPassword())
					.uniqueResult();
		}else{
			//如果用户输入的是用户名
			if(user.getUsername() != null){
				return (User)getSession().createQuery(usernameHql)
						.setString("username",user.getUsername())
						.setString("password",user.getPassword())
						.uniqueResult();
			}
		}
		return null;
	}

	@Override
	public User checkToken(int id, String token){
		String hql = "FROM User u WHERE u.id=:id AND u.token=:token";
		return (User)getSession().createQuery(hql)
				.setInteger("id",id)
				.setString("token",token)
				.uniqueResult();
	}

	@Override
	public User getUser(String phone) {
		String hql = "FROM User u WHERE u.phone=:phone";
		return (User)getSession().createQuery(hql).setString("phone",phone).uniqueResult();
	}

	@Override
	public void updateToken(String token , int uid){
		String hql = "UPDATE User u SET u.token=:token WHERE u.id=:uid";
		getSession().createQuery(hql)
				.setString("token", token)
				.setInteger("uid", uid)
				.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User_follow_Business> getUserFollowsBusiness(int uid , int pageNum , int pageSize){

		int fromIndex = pageSize * (pageNum-1);
		String hql = "FROM User_follow_Business ufb"
				+ " JOIN FETCH ufb.business"
				+ " WHERE ufb.user.id=:uid";
		return getSession().createQuery(hql)
				.setInteger("uid", uid)
				.setFirstResult(fromIndex)
				.setMaxResults(pageSize)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User_follow_Product> getUserFollowsProduct(int id, int pageNum, int pageSize) {
		
		int fromIndex = pageSize * (pageNum - 1);
		String hql = "FROM User_follow_Product ufp"
				+ " JOIN FETCH ufp.product p"
				+ " JOIN FETCH ufp.user u"
				+ " JOIN FETCH p.category"
				+ " JOIN FETCH p.protypeList"
				+ " JOIN FETCH p.business"
				+ " WHERE u.id=:id";
		return getSession().createQuery(hql)
						.setInteger("id", id)
						.list();
		
	}
	
}
