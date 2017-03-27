package com.hr.shop.util;

import com.hr.shop.model.Category;
import com.hr.shop.model.Product;
import com.hr.shop.service.CategoryService;
import com.hr.shop.service.ProductService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
/**
 * @author hjc
 * 设置任务：run方法中用来加载首页商品信息数据
 */
@Component("productTimerTask")
public class ProdoctTimerTask extends TimerTask {

	@Resource
	private ProductService productService;
	@Resource
	private CategoryService categoryService;
	
	private ServletContext application ;
	
	public void setApplication(ServletContext application) {
		this.application = application;
	}
	
	@Override
	public void run() {
		List<List<Product>> bigList = new ArrayList<List<Product>>();
		//查询出热点类别
		for(Category category :categoryService.queryByHot(true)){
			//根据热点类别id获取推荐商品信息
			bigList.add(productService.queryByCid(category.getId()));
		}
		//把查询的bigList交给application内置对象
		application.setAttribute("bigList", bigList);
	}
}
