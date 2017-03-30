package com.hr.shop.serviceImpl;

import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.dao.*;
import com.hr.shop.service.BaseService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service("baseService")
@Lazy(true)
public class BaseServiceImpl<T> implements BaseService<T> {

	private Class clazz;  //clazz中存储了当前操作的类型
	
	public  BaseServiceImpl(){
		
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class)type.getActualTypeArguments()[0];
	}
	
	@PostConstruct
	public void init(){
		String clazzName = clazz.getSimpleName();
		String clazzDao = clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1)+"Dao";
		try{
			Field clazzField = this.getClass().getSuperclass().getDeclaredField(clazzDao);
			Field baseField = this.getClass().getSuperclass().getDeclaredField("baseDao");
			baseField.set(this,clazzField.get(this));
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	protected BaseDao baseDao;
	
	@Resource
	protected AccountDao accountDao;
	@Resource
	protected CategoryDao categoryDao;
	@Resource
	protected ForderDao forderDao;
	@Resource
	protected ProductDao productDao;
	@Resource
	protected SorderDao sorderDao;
	@Resource
	protected UserDao userDao;
	@Resource
	protected CartDao cartDao;
	@Resource
	protected ProtypeDao protypeDao;
	
	@Override
	public void save(T t) {
		baseDao.save(t);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	public void delete(int id) {
		baseDao.delete(id);
	}

	
	@Override
	public T get(int id) {
		return (T)baseDao.get(id);
	}

	@Override
	public List<T> query() {
		return baseDao.query();
	}

	@Override
	public Map<String, Object> successRespMap(Map<String,Object> map , String message , Object data){
		if (map == null){
			map = new HashMap<String, Object>();
		}
		map.put("error_code" , "0");
		map.put("message" , message);
		map.put("data", data);
		return map;
	}

	@Override
	public Map<String, Object> errorRespMap(Map<String,Object> map , String message){
		if (map == null){
			map = new HashMap<String, Object>();
		}
		map.put("error_code" , "-1");
		map.put("message" , message);
		map.put("data", "");
		return map;
	}
}
