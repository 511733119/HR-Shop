package com.hr.shop.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hr.shop.dao.AccountDao;
import com.hr.shop.dao.BaseDao;
import com.hr.shop.dao.CategoryDao;
import com.hr.shop.dao.ForderDao;
import com.hr.shop.dao.ProductDao;
import com.hr.shop.dao.SorderDao;
import com.hr.shop.dao.UserDao;
import com.hr.shop.model.Product;
import com.hr.shop.service.BaseService;
/**
 * @author hjc
 * 公共dao接口实现类
 */
@SuppressWarnings("unchecked")
@Repository("baseDao")
@Lazy(true)
public class BaseDaoImpl<T> implements BaseDao<T> {

	private Class clazz;  //clazz中存储了当前操作的类型
	
	@Resource
	private SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public  BaseDaoImpl(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class)type.getActualTypeArguments()[0];
	}
	
	@Override
	public void save(T t) {
		getSession().save(t);
	}

	@Override
	public void update(T t) {
		getSession().update(t);
	}

	@Override
	public void delete(int id) {
		String hql = "DELETE FROM "+ clazz.getSimpleName()+ " WHERE id=:id";
		getSession().createQuery(hql)
				.setInteger("id", id)
				.executeUpdate();
	}

	@Override
	public T get(int id) {
		return (T) getSession().get(clazz, id);
	}

	@Override
	public List<T> query() {
		String hql = "FROM "+ clazz.getSimpleName();
		return getSession().createQuery(hql).list();
	}


}
