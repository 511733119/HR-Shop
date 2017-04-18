package com.hr.shop.dao;

import com.hr.shop.model.Product;

import java.util.List;
/**
 * @author hjc
 * 商品dao接口
 */
public interface ProductDao extends BaseDao<Product> {

	public List<Product> queryJoinCategory(String name, int page, int size);

	/**
	 * 根据关键字查询总记录数
	 * @param name 输入关键字
	 * @return
	 */
	public Long getCount(String name);
	
	/**
	 * 根据类别查询商品
	 * @param cid 商品种类id
	 * @return
	 */
	public List<Product> queryByCid(int cid);

	/**
	 * 根据关键字获得商品名称
	 * @param name 输入关键字
	 * @return
	 */
	public List<Product> getSimilarProduct(String name ,int pageNum,int pageSize) ;

	/**
	 * 分类页展示分类商品数据
	 * @param pageNum 页码
	 * @param pageSize 每页显示条数
	 * @param id 商品种类id
	 * @return
	 */
	public List<Product> findProduct(int pageNum, int pageSize, int id);

	/**
	 * 首页展示新上架/销量最高商品数据
	 * @param pageNum 页码
	 * @param pageSize 每页显示条数
	 * @param field  字段名，根据字段名判断取出新上架商品还是销量最高商品
	 * @return
	 */
	public List<Product> findNewest_Or_HighestSalesProduct(int pageNum, int pageSize, String field);

	/**
	 * 点击商品进入商品详情页
	 * @param pid 商品id
	 * @return
	 */
	public List<Product> getProduct(int pid);

	/**
	 * 列出搜索页的推荐商品
	 * @return
	 */
	public List<Product> getSearchList();

	/**
	 * 用户支付成功后，更新商品的销量
	 * @param pid
	 * @param buy_number
	 */
	public void updateSales(int pid, int buy_number);
}
