package com.hr.shop.action;

import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author hjc
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountAction extends BaseAction<Account> {


	@RequestMapping(value = "/", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public Map<String, Object> query(){
		jsonList = accountService.query();
		return productService.successRespMap(respMap, Map_Msg.SUCCESS, jsonList);
	}
	
}
