package com.hr.shop.serviceImpl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.hr.shop.model.User_follow_Business;
import com.hr.shop.service.User_follow_BusinessService;

/**
 *用户关注店铺类服务接口实现类
 * @author hjc
 *
 */
@Service("user_BusinessService")
public class User_follow_BusinessServiceImpl extends BaseServiceImpl<User_follow_Business> implements User_follow_BusinessService {

	@Override
	public long checkIfFollow(int id, int uid) {
		return user_follow_BusinessDao.checkIfFollow(id, uid);
	}

	@Override
	public void deleteFollow(int id, int uid) {
		user_follow_BusinessDao.deleteFollow(id, uid);
	}

}
