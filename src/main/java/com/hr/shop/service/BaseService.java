package com.hr.shop.service;

import java.util.List;
import java.util.Map;


public interface BaseService<T> {

	public void save(T t);
	
	public void update(T t) ;
	
	public void delete(int id) ;
	
	public T get(int id) ;
	
	public List<T>  query();

	/**
	 * 请求返回Map
	 * @param dataMap
	 * @param status 状态
	 * @param object 消息内容
	 * @return
	 */
	public Map<String,Object> getDataMap(Map<String,Object> dataMap , String status, Object object);

}
