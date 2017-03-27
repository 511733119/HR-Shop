package com.hr.shop.daoImpl;

import org.springframework.stereotype.Repository;
import com.hr.shop.dao.AccountDao;
import com.hr.shop.model.Account;

/**
 * @author hjc
 * 管理员dao实现类
 */
@Repository("accountDao")
public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao {
	
}
