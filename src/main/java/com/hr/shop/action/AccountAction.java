package com.hr.shop.action;

import com.hr.shop.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hjc
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountAction extends BaseAction<Account> {


	@RequestMapping(value = "/", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public ResponseEntity<List<Account>> query(){
		jsonList = accountService.query();
		return new ResponseEntity<List<Account>>(jsonList, HttpStatus.OK);
	}

	
}
