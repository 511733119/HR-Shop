package com.hr.shop.serviceImpl;

import org.springframework.stereotype.Service;

import com.hr.shop.model.Account;
import com.hr.shop.service.AccountService;

/**
 * 管理员服务接口实现类
 * @author hjc
 */
@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl<Account> implements AccountService {
	
}
