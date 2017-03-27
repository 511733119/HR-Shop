package com.hr.shop.serviceImpl;

import com.google.gson.Gson;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.model.*;
import com.hr.shop.service.ForderService;
import com.hr.shop.service.ProtypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

/*
 * 模块自身的业务逻辑
 */
@Service("forderService")
public class ForderServiceImpl extends BaseServiceImpl<Forder> implements ForderService {

	@Resource
	private ProtypeService protypeService;
	
	@Override
	public void updateStatusById(int id, int sid) {
		forderDao.updateStatusById(id, sid);
	}

	@Override
	public void saveOrder(String json, User user, Status status, Forder forder) {
		//解析前台json数据
		Order order = parseJson(json);
		if (order == null){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}

		BigDecimal price ;
		BigDecimal total = new BigDecimal(0.00);

		//初始化Forder中的Sorder集合
		forder.setSorderSet(new HashSet<Sorder>());

		/*
		 * 遍历Order对象，set Forder中的Sorder集合
		 */
		Sorder sorder = new Sorder();
		for (int i = 0; i < order.getCart().size(); i++) {
			price = new BigDecimal(0.00);
			Cart cart = order.getCart().get(i);
			int cartid = cart.getId();
			BigDecimal bd = new BigDecimal(cart.getNumber()).multiply(cart.getProtype().getProduct().getPrice());//该订单项单件商品总金额
			price = price.add(bd);
			//forder与sorder相关联
			setForderSet(sorder,cart,forder,price);
			//减少库存
			// 这里需要再从数据库拿出库存数量，是为了防止用户在购物车页面停留过久，
			// 而在此过程中其他用户购买该商品，将使得库存信息不一致
			updateInventory(cartid , cart);
			total = total.add(price);//计算订单总金额
		}
		//订单信息设置
		setForder(forder , order , status , user , total);
		//保存订单
		save(forder);
	}

	private Forder setForder(Forder forder ,Order order, Status status,User user , BigDecimal total ){
		forder.setId(String.valueOf(System.currentTimeMillis())); //设置订单号
		forder.setStatus(status);
		forder.setUser(user);
		forder.setAddress(order.getAddress());
		forder.setName(order.getName());
		forder.setPhone(order.getPhone());
		forder.setRemark(order.getRemark());
		forder.setTotal(total);
		return forder;
	}
	/**
	 * forder与sorder相关联
	 * @param sorder
	 * @param cart
	 * @param forder
	 * @param price
	 */
	private void setForderSet(Sorder sorder, Cart cart, Forder forder , BigDecimal price){
		sorder.setNumber(cart.getNumber());	//设置每个订单项数量
		sorder.setProtype(cart.getProtype());	//设置每个订单项对应的商品细分种类
		sorder.setForder(forder);	//设置每个订单项对应的订单
		sorder.setPrice(price);	//设置每个订单项的总金额
		forder.getSorderSet().add(sorder);	//将订单与订单项关联起来
	}
	/**
	 * 减少库存
	 * @param id
	 * @param cart
	 */
	private void updateInventory(int id , Cart cart){
		Protype protype = cartDao.get(id).getProtype();
		protype.setInventory(protype.getInventory() - cart.getNumber());
		//写入数据库
		protypeService.update(protype);
	}

	/**
	 * 解析json为Order对象
	 * @param json
	 * @return
	 */
	private Order parseJson(String json){
		Gson gson =  new Gson();
		return gson.fromJson(json, Order.class);
	}
	@Override
	public List<Forder> findAllOrder(int uid, int pageNum, int pageSize) {
		return forderDao.findAllOrder(uid, pageNum, pageSize);
	}

	@Override
	public int cancelOrder(String id) {
		return forderDao.cancelOrder(id);
	}

	@Override
	public int deleteOrder(String id) {
		return forderDao.deleteOrder(id);
	}

	@Override
	public Forder getOrder(String id) {
		return forderDao.getOrder(id);
	}
}
