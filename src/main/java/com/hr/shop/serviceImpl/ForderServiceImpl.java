package com.hr.shop.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.dto.Order;
import com.hr.shop.dto.Pay_Result;
import com.hr.shop.model.*;
import com.hr.shop.service.ForderService;
import com.hr.shop.service.ProtypeService;
import com.hr.shop.util.Config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/*
 * 模块自身的业务逻辑
 */
@Service("forderService")
public class ForderServiceImpl extends BaseServiceImpl<Forder> implements ForderService {

	@Resource
	private ProtypeService protypeService;
	
	@Resource(name="config")
	private Config config;
	
	@Override
	public void updateStatusById(String id, int sid) {
		forderDao.updateStatusById(id, sid);
	}

	@Override
	public String saveOrder(String json, User user, Status status, Forder forder) {
		//解析前台json数据
		Order order = parseJson(json);
		//如果解析失败，即json格式错误
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
		boolean bool;
		StringBuilder stringBuilder = new StringBuilder();//用于保存所要删除购物车项的id字符串
		
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
			bool = updateInventory(cartid , cart);
			if (!bool) {
				return "0";
			}
			total = total.add(price);//计算订单总金额

			//向StringBuilder中添加购物车项id
			if(i == 0){
				stringBuilder.append(cartid);
			}else{
				stringBuilder.append(",").append(cartid);
			}
		}
		//订单信息设置
		Forder forder2 = setForder(forder , order , status , user , total);
		//保存订单
		save(forder);
		cartDao.deleteCart(stringBuilder.toString()); //下单成功，删除所买商品在购物车中的展示
		return forder2.getId();
	}

	@Override
	public Forder setForder(Forder forder ,Order order, Status status,User user , BigDecimal total ){
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
	
	@Override
	public void setForderSet(Sorder sorder, Cart cart, Forder forder , BigDecimal price){
		sorder.setNumber(cart.getNumber());	//设置每个订单项数量
		sorder.setProtype(cart.getProtype());	//设置每个订单项对应的商品细分种类
		sorder.setForder(forder);	//设置每个订单项对应的订单
		sorder.setPrice(price);	//设置每个订单项的总金额
		forder.getSorderSet().add(sorder);	//将订单与订单项关联起来
	}
	
	@Override
	public boolean updateInventory(int id , Cart cart){
		Protype protype = cartDao.get(id).getProtype();
		int protype_inventory = protype.getInventory();
		int cart_num = cart.getNumber();
		if(protype_inventory < cart_num){
			return false;
		}
		protype.setInventory(protype.getInventory() - cart.getNumber());
		//写入数据库
		protypeService.update(protype);
		return true;
	}

	@Override
	public Order parseJson(String json){
		Gson gson =  new Gson();
		return gson.fromJson(json, Order.class);
	}
	
	@Override
	public List<Forder> findAllOrder(int uid, int pageNum, int pageSize) {
		return forderDao.findAllOrder(uid, pageNum, pageSize);
	}

	@Override
	public int deleteOrder(String id) {
		return forderDao.deleteOrder(id);
	}

	@Override
	public Forder getOrder(String id) {
		return forderDao.getOrder(id);
	}

	@Override
	public void updateTimeAndStatus(String id, String create_time, int sid) {
		forderDao.updateTimeAndStatus(id, create_time, sid);
	}
	
	@Override
	public String getPay_json(String url){
		 //创建一个默认的HttpClient  
        HttpClient httpclient = new DefaultHttpClient();  
        //以get方式请求网页查询订单状态  
        HttpGet httpget = new HttpGet(url); 
        System.out.println(config.getApplication_id()+",,"+config.getRest_api_key());
        httpget.addHeader("X-Bmob-Application-Id", config.getApplication_id());
        httpget.addHeader("X-Bmob-REST-API-Key", config.getRest_api_key());
        //创建响应处理器处理服务器响应内容  
        ResponseHandler<String> responseHandler = new BasicResponseHandler();  
        //执行请求并获取结果  
        String responseBody = null;
		try {
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (IOException e) {
			throw new RuntimeException("解析json数据失败");
		}  
		return responseBody;
	}
	
	@Override
	public Pay_Result parsePay_json(String responseBody){
		GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期的格式，遇到这个格式的数据转为Date对象
        Gson gson = gsonBuilder.create();
        Pay_Result pay_Result = gson.fromJson(responseBody, Pay_Result.class); //解析json数据为Pay_Result类
        return pay_Result;
	}
}
