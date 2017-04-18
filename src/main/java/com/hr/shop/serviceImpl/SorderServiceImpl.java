package com.hr.shop.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hr.shop.model.Sorder;
import com.hr.shop.service.SorderService;

/**
 * 订单项类服务接口实现类
 * @author hjc
 *
 */
@Service("sorderService")
public class SorderServiceImpl extends BaseServiceImpl<Sorder> implements SorderService {

	@Override
	public void updateComm_flag(int id, int comm_flag) {
		sorderDao.updateComm_flag(id, comm_flag);
	}

	@Override
	public List<Object[]> getSorderList(String fid) {
		return sorderDao.getSorderList(fid);
	}
}
