package com.hr.shop.serviceImpl;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.hr.shop.service.CategoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class AccountServiceImplTest {

	@Resource
	private CategoryService categoryService;
	
	@Test
	public void testQuery() {
		Gson gson = new Gson();
		String json = gson.toJson(categoryService.query());
		System.out.println(json);
	}

}
