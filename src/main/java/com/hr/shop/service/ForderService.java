package com.hr.shop.service;

import com.hr.shop.dto.Order;
import com.hr.shop.dto.Pay_Result;
import com.hr.shop.model.Cart;
import com.hr.shop.model.Forder;
import com.hr.shop.model.Sorder;
import com.hr.shop.model.User;
import com.hr.shop.model.Status;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 订单服务接口类
 * @author hjc
 *
 */
public interface ForderService extends BaseService<Forder> {
	/**
	 * 下订单
	 * @param json 前台json数据
	 * @param user 用户对象
	 * @param status 状态对象
	 * @param forder 订单对象
	 */
	public String saveOrder(String json, User user, Status status, Forder forder);
	/**
	 * 根据订单编号，更新订单状态
	 * @param id 订单id
	 * @param sid 状态id
	 */
	public void updateStatusById(String id, int sid);
	
	/**
	 * 支付成功修改支付时间和订单状态
	 * @param id 订单id
	 * @param create_time 支付时间
	 * @param sid 订单状态id
	 */
	public void updateTimeAndStatus(String id, String create_time, int sid);
	
	/**
	 * 查询用户对应的全部订单
	 * @param uid 用户id
	 * @param pageNum 页码
	 * @param pageSize 每页显示pagesize条数据
	 * @return
	 */
	public List<Forder> findAllOrder(int uid, int pageNum, int pageSize);

	/**
	 * 删除订单
	 * @param id  订单id
	 * @return
	 */
	public int deleteOrder(String id);

	/**
	 * 根据订单号查找订单
	 * @param id
	 * @return
	 */
	public Forder getOrder(String id);
	
	/**
	 * HttpClient访问bmob查询订单获取订单信息
	 * @param url
	 * @return
	 */
	public String getPay_json(String url);
	
	/**
	 * 解析获取到的订单json信息为Pay_Result类
	 * @param responseBody json数据 
	 * @return
	 */
	public Pay_Result parsePay_json(String responseBody);
	
	/**
	 * 下单给Forder类赋值
	 * @param forder
	 * @param order
	 * @param status
	 * @param user
	 * @param total
	 * @return
	 */
	public Forder setForder(Forder forder ,Order order, Status status,User user , BigDecimal total );
	/**
	 * 解析json为Order对象
	 * @param json
	 * @return
	 */
	public Order parseJson(String json);
	
	/**
	 * 减少库存
	 * @param id
	 * @param cart
	 */
	public boolean updateInventory(int id , Cart cart);
	
	/**
	 * forder与sorder相关联
	 * @param sorder
	 * @param cart
	 * @param forder
	 * @param price
	 */
	public void setForderSet(Sorder sorder, Cart cart, Forder forder , BigDecimal price);
	
	/**
	 * 取消订单，修改订单状态及恢复库存
	 * @param forder
	 * @param fid 订单id
	 */
	public void cancelOrder(Forder forder, String fid);
	
	/**
	 * HttpClient抓取bmob订单状态
	 * @param id bmob订单id
	 * @return
	 */
	public Pay_Result getPay_Result(String id);
	
	/**
	 * 更新订单支付时间和订单状态修改为已支付，更新商品销量
	 * @param pay_Result
	 * @param fid 订单id
	 */
	public void updateAboutForder(Pay_Result pay_Result, String fid);
	
}
