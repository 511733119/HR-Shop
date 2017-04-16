package com.hr.shop.serviceImpl;

import org.springframework.stereotype.Service;

import com.hr.shop.model.Sorder;
import com.hr.shop.service.SorderService;

/*
 * 模块自身的业务逻辑
 */
@Service("sorderService")
public class SorderServiceImpl extends BaseServiceImpl<Sorder> implements SorderService {

	@Override
	public void updateComm_flag(int id, int comm_flag) {
		sorderDao.updateComm_flag(id, comm_flag);
	}
}
