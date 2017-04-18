package com.hr.shop.action;

import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.model.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author hjc
 * 商品种类Action，实现查询所有商品种类等的相关操作
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryAction extends BaseAction<Category> {

	@RequestMapping(value = "/", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public Map<String, Object> query(){
		jsonList = categoryService.query();
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , jsonList);
	}
	
}
