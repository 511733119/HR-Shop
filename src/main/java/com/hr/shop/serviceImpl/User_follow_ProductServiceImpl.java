package com.hr.shop.serviceImpl;

import org.springframework.stereotype.Service;
import com.hr.shop.model.User_follow_Product;
import com.hr.shop.service.User_follow_ProductService;

/**
 *用户关注店铺类服务接口实现类
 * @author hjc
 *
 */
@Service("user_ProductService")
public class User_follow_ProductServiceImpl extends BaseServiceImpl<User_follow_Product> implements User_follow_ProductService {

	@Override
	public void deleteFollow(int pid, int id) {
		user_follow_ProductDao.deleteFollow(pid, id);
	}
	
	@Override
	public long checkIfFollow(int pid, int id) {
		return user_follow_ProductDao.checkIfFollow(pid, id);
	}
}
