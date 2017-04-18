package com.hr.shop.action;

import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.model.Business;

import java.util.Map;

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

	@RequestMapping(value = "/" , method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Map<String, Object> save(int id){
		Business business = businessService.get(id);
		return productService.successRespMap(respMap, Map_Msg.SUCCESS, "");
	}
}
