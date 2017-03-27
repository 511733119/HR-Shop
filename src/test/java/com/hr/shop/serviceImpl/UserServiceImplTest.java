package com.hr.shop.serviceImpl;

import com.hr.shop.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class UserServiceImplTest {

	@Resource
	private UserService userService;
	
	@Test
	public void testSendCode() {
//		String body = userService.sendCode("15626401344");
//		System.out.println(body);
	}

	@Test
	public void testGetUser(){
//		System.out.println(userService.getUserPhone());
	}
	
}
