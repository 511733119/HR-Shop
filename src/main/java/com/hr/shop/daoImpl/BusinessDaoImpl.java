package com.hr.shop.daoImpl;

import com.hr.shop.dao.BusinessDao;
import com.hr.shop.dao.ProductDao;
import com.hr.shop.model.Business;
import com.hr.shop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author hjc
 * 商家dao接口实现类
 */
@Repository("businessDao")
public class BusinessDaoImpl extends BaseDaoImpl<Business> implements BusinessDao {

}
