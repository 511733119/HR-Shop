package com.hr.shop.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hr.shop.model.Product;
import com.hr.shop.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-*.xml")
public class ProductServiceImplTest {

	@Resource
	private ProductService productService;
	
	@Test
	public void testQuery() {
		List<Product> productList = productService.findProduct(0, 16,1);
		System.out.println(JSON.toJSONString(productList));
//		System.out.println(JSON.toJSONString(productList.get(1)));
	}
	
	@Test
	public void testGet(){
		System.out.println(new Gson().toJson(productService.get(1)));
//		for(Protype protype : productService.getProduct(1)){
//			System.out.println(protype.getName()+" "+protype.getPic());
//		}
//		Gson gson = new Gson();
//		String string = gson.toJson(productService.getProduct(1)).toString();
//		System.out.println(string);
	}

	@Test
	public void testFindNewestProduct(){
		List<Product> jList = productService.findNewest_Or_HighestSalesProduct(1, 12,"sales");
		for (Product product : jList){
			System.out.println(product.getProtypeSet()+","+product.getId()+","+product.getName()+","+product.getPrice());
		}
		System.out.println(jList);
	}
}

