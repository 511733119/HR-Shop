package com.hr.shop.service;

import java.util.List;
import java.util.Map;

import com.hr.shop.dto.Comment_Page;
import com.hr.shop.model.Sorder;

public interface SorderService extends BaseService<Sorder> {

	/**
	 * 更新评论标志
	 * @param id
	 * @param comm_flag
	 */
	public void updateComm_flag(int id , int comm_flag);
}
