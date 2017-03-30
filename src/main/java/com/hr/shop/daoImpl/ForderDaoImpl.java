package com.hr.shop.daoImpl;

import com.hr.shop.dao.ForderDao;
import com.hr.shop.model.Forder;
import com.hr.shop.model.Sorder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hjc
 */
@Repository("forderDao")
public class ForderDaoImpl extends BaseDaoImpl<Forder> implements ForderDao {

	@Override
	public void updateStatusById(int id, int sid) {
		String hql = "UPDATE Forder f SET f.status.id=:sid WHERE f.id=:id";
		getSession().createQuery(hql)
					.setInteger("sid", sid)
					.setInteger("id", id)
					.executeUpdate();
	}

	@Override
	public List<Forder> findAllOrder(int uid, int pageNum, int pageSize) {

		int fromIndex = pageSize * (pageNum-1);

		String hql = "FROM Forder f JOIN FETCH f.sorderSet st JOIN FETCH st.protype pt JOIN FETCH pt.product p JOIN FETCH p.category JOIN FETCH p.protypeSet JOIN FETCH f.status JOIN FETCH f.user WHERE f.user.id=:uid ORDER BY f.update_date desc";
		return getSession().createQuery(hql)
				.setInteger("uid",uid)
				.setFirstResult(fromIndex)
				.setMaxResults(pageSize)
				.setCacheable(true)
				.list();
	}

	@Override
	public int deleteOrder(String id) {
		String hql = "UPDATE Forder SET flag=1 WHERE id=:id";
		getSession().createQuery(hql).setString("id",id).executeUpdate();
		return 1;
	}

	@Override
	public int cancelOrder(String id) {
		String hql = "UPDATE Forder f SET f.status.id=4 WHERE f.id=:id";
		getSession().createQuery(hql).setString("id",id).executeUpdate();
		return 1;
	}

	@Override
	public Forder getOrder(String id) {
		String hql = "FROM Forder WHERE id=:id";
		return (Forder)getSession().createQuery(hql).setString("id",id).uniqueResult();
	}
}
