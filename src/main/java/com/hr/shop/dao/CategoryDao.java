package com.hr.shop.dao;

import com.hr.shop.model.Category;

import java.util.List;
/**
 * @author hjc
 * 商品种类dao接口
 */
public interface CategoryDao extends BaseDao<Category> {

	/**
	 * 查询类别信息，级联管理员
	 * @param page 页码
	 * @param size 每页显示size条数据
	 * @param type 商品种类
	 */
	public List<Category> queryJoinAccount(String type, int page, int size);

	/**
	 * 根据关键字查询总记录数
	 * @param type
	 * @return
	 */
	public Long getCount(String type);

	/**
	 * 根据id删除多条记录
	 * @param ids
	 */
	public void deleteByIds(String ids);

	/**
	 * 根据boolean查询热点或非热点类别
	 * @param hot
	 * @return
	 */
	public List<Category> queryByHot(boolean hot);
	
}
