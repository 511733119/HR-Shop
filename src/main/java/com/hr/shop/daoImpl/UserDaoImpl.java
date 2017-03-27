package com.hr.shop.daoImpl;
import com.hr.shop.dao.UserDao;
import com.hr.shop.model.User;
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


}
