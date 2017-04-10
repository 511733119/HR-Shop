package com.hr.shop.serviceImpl;

import com.google.gson.Gson;
import com.hr.shop.dto.Order;
import com.hr.shop.model.*;
import com.hr.shop.service.ForderService;
import com.hr.shop.service.ProtypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class ForderServiceImplTest {


	private ForderService forderService = new ForderServiceImpl();
	@Resource
	private ProtypeService protypeService;
	/*
	 * 测试提交订单
	 */
	@Test
	public void testSaveOrder() {
		String json = "{'name':'海俊','address':'清风阁','remark':'加两双筷子','phone':'123124325235','cart':[{'id':1,'number':43,'protype':{'id':4,'name':'ddd','pic':'4.jpg','inventory':627,'product':{'id':2,'name':'bbb','price':23}}},{'id':6,'number':3,'protype':{'id':2,'name':'bbb','pic':'2.jpg','inventory':751,'product':{'id':1,'name':'aa','price':21}}}]}";
		Gson gson =  new Gson();
		Order order = gson.fromJson(json, Order.class);
		User user = new User(1, "fsdfsd", "gregwerg","1231141241");
		Status status = new Status(1);
		Forder forder = new Forder();
		BigDecimal price ;
		BigDecimal total = new BigDecimal(0.00);
		forder.setSorderSet(new HashSet<Sorder>());
		forder.setId(String.valueOf(System.currentTimeMillis())); //设置订单号
		for (int i = 0; i < order.getCart().size(); i++) {
			price = new BigDecimal(0.00);
			Sorder sorder = new Sorder();
			Cart cart = order.getCart().get(i);
			sorder.setNumber(cart.getNumber());
			sorder.setProtype(cart.getProtype());
			sorder.setForder(forder);
			price = price.add(new BigDecimal(cart.getNumber()).multiply(cart.getProtype().getProduct().getPrice()));
			sorder.setPrice(price);
			total = total.add(price);
			forder.getSorderSet().add(sorder);
			//减少库存
			Protype protype = cart.getProtype();
			protype.setInventory(protype.getInventory() - cart.getNumber());
			//写入数据库
			protypeService.update(protype);
		}
		
		forder.setStatus(status);
		forder.setUser(user);
		forder.setAddress(order.getAddress());
		forder.setName(order.getName());
		forder.setPhone(order.getPhone());
		forder.setRemark(order.getRemark());
		forder.setTotal(total);
		
		
		forderService.save(forder);
	}

	@Test
	public void testFindAllOrder(){
		List<Forder> List = forderService.findAllOrder(1,1,10);
		System.out.println(List);
	}
}
