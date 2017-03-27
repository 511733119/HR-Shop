package com.hr.shop.serviceImpl;

import com.hr.shop.model.Cart;
import com.hr.shop.model.Protype;
import com.hr.shop.model.User;
import com.hr.shop.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.List;

/*
 * 模块自身的业务逻辑
 */
@Service("cartService")
public class CartServiceImpl extends BaseServiceImpl<Cart> implements CartService {

	@Override
	public List<Cart> getCartId(int uid) {
		return cartDao.getCartId(uid);
	}

	@Override
	public List<Cart> getCart(int uid) {
		return cartDao.getCart(uid);
	}
	
	@Override
	public void saveCart(List<Cart> List, int number, User user,Protype protype) {
		//标志是否有重复数据
		boolean isHave = false;
		for(Cart cart : List){
			if(cart.getProtype().getId() == protype.getId()){
				//直接更新数量
				cart.setNumber(cart.getNumber()+number);
				update(cart);
				isHave = true;
				return;
			}
		}
		//如果没有重复的商品，则添加一条购物车记录
		if(!isHave){
			Cart cart = new Cart(number);
			cart.setProtype(protype);
			cart.setUser(user);
			save(cart);
		}
	}

	@Override
	public void deleteByCheck(String ids) {
		cartDao.deleteByCheck(ids);
	}

	@Override
	public int checkInventory(String[] ptid , String[] number) {
		int i = cartDao.checkInventory(ptid,number);
		if( i < ptid.length){
			return 1;//库存不足，无法下单
		}
		return 0;//库存足够，可以下单
	}
}
