package com.hr.shop.daoImpl;

import com.hr.shop.dao.SorderDao;
import com.hr.shop.model.Sorder;
import org.springframework.stereotype.Repository;
/**
 * @author hjc
 * 订单项dao接口实现类
 */
@SuppressWarnings("unchecked")
@Repository("sorderDao")
public class SorderDaoImpl extends BaseDaoImpl<Sorder> implements SorderDao {

}
