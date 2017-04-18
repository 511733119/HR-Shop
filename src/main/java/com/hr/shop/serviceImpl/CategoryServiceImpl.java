package com.hr.shop.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hr.shop.model.Category;
import com.hr.shop.service.CategoryService;

/**
 * 商品种类服务接口实现类
 * @author hjc
 *
 */
@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

	@Override
	public List<Category> queryJoinAccount(String type,int page,int size) {
		return categoryDao.queryJoinAccount(type, page, size);
	}

	@Override
	public Long getCount(String type) {
		return categoryDao.getCount(type);
	}

	@Override
	public void deleteByIds(String ids) {
		categoryDao.deleteByIds(ids);
	}

	@Override
	public List<Category> queryByHot(boolean hot) {
		return categoryDao.queryByHot(hot);
	}

}
