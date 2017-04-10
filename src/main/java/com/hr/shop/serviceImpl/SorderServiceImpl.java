package com.hr.shop.serviceImpl;

import com.hr.shop.dto.Sorder_Comment;
import com.hr.shop.model.Sorder;
import com.hr.shop.service.SorderService;

import java.util.List;

import org.springframework.stereotype.Service;

/*
 * 模块自身的业务逻辑
 */
@Service("sorderService")
public class SorderServiceImpl extends BaseServiceImpl<Sorder> implements SorderService {

	@Override
	public Sorder_Comment getSorder(int id){
		return sorderDao.getSorder(id);
	}
}
