package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.Account;
import com.hr.shop.response.RestResultGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hjc
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountAction extends BaseAction<Account> {

	@JsonView(View.view.class)
	@RequestMapping(value = "/", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String query(){
		jsonList = accountService.query();
		respRes = RestResultGenerator.genResult(Map_Msg.HTTP_OK , jsonList);
		return respRes.toString();
	}

	
}
