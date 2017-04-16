package com.hr.shop.daoImpl;

import com.hr.shop.dao.Append_CommentDao;
import com.hr.shop.dao.CommentDao;
import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 追加评论接口实现类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/31 20:30
 */
@Repository
public class Append_CommentDaoImpl extends BaseDaoImpl<Append_Comment> implements Append_CommentDao {

	@Override
	public List<Append_Comment> getAppend_Comment(int pid, int uid){
		String hql = "SELECT ac.id FROM Append_Comment ac"
				+ " WHERE ac.product.id=:pid"
				+ " AND ac.user.id=:uid";
		return getSession().createQuery(hql)
				.setInteger("pid", pid)
				.setInteger("uid", uid)
				.list();
	}
}
