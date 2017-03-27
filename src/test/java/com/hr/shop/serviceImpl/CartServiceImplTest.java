package com.hr.shop.serviceImpl;

import com.hr.shop.model.Cart;
import com.hr.shop.model.User;
import com.hr.shop.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class CartServiceImplTest {

	@Resource
	private CartService cartService;
	
	@Test
	public void testQuery() {
		User user = new User(3, "dwef", "fewfw","14142535353");
		List<Cart> List = cartService.getCart(user.getId());
		for (int i = 0; i < List.size(); i++) {
			System.out.println(List.get(i).getProtype().getName());
		}
	}

}
