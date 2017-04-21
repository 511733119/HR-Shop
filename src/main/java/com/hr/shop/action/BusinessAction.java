package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.Business;
import com.hr.shop.model.Product;
import com.hr.shop.model.User;
import com.hr.shop.model.User_follow_Business;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hjc
 * 商家Action，实现商家模块的相关操作
 */
@RestController
@RequestMapping("/api/businesses")
public class BusinessAction extends BaseAction<Business>{

	/**
	 * 店铺首页展示店铺及其全部商品数据
	 * @param id
	 * @param pageNum
	 * @return
	 */
	@JsonView({View.son2.class})
	@RequestMapping(value = "/{id}" , method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Map<String, Object> getInto_Business(@PathVariable("id") int id , int pageNum){
		if (id < 1 || pageNum <= 0) {
			return productService.errorRespMap(respMap, Map_Msg.PARAM_IS_INVALID);
		}
		List<Product> jsonList = businessService.getInto_Business(id , pageNum , 10);
		return productService.successRespMap(respMap, Map_Msg.SUCCESS, jsonList);
	}
	
	/**
	 * 店铺页展示上新商品数据
	 * @param id
	 * @param pageNum
	 * @return
	 */
	@JsonView({View.son2.class})
	@RequestMapping(value = "/new-products/{id}" , method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Map<String, Object> getNew_Product(@PathVariable("id") int id , int pageNum){
		if (id < 1 || pageNum <= 0) {
			return productService.errorRespMap(respMap, Map_Msg.PARAM_IS_INVALID);
		}
		List<Product> jsonList = businessService.getNew_Product(id , pageNum , 10);
		return productService.successRespMap(respMap, Map_Msg.SUCCESS, jsonList);
	}
	
}
