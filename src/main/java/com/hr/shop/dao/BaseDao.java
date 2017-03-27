package com.hr.shop.dao;

import java.util.List;

/**
 * @author hjc
 * 公共dao接口
 * @param <T> 类名泛型
 */
public interface BaseDao<T> {

	public void save(T t);
	
	public void update(T t) ;
	
	public void delete(int id) ;
	
	public T get(int id) ;
	
	public List<T> query();

}
