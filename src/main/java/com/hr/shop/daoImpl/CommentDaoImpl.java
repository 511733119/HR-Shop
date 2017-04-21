package com.hr.shop.daoImpl;

import com.hr.shop.dao.CommentDao;
import com.hr.shop.dto.Comment_dto;
import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/31 20:30
 */
@Repository
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements CommentDao {

    @SuppressWarnings("unchecked")
	@Override
    public List<Comment> getProductComment(int pid , int pageNum , int pageSize){
        int fromIndex = pageSize * (pageNum-1);
        String hql = "FROM Comment c" +
                " LEFT JOIN FETCH c.append_comment a" +
                " LEFT JOIN FETCH c.comment_pic_List" +
                " LEFT JOIN FETCH a.append_comment_pic_List" +
                " JOIN FETCH c.product p" +
                " JOIN FETCH c.user" +
                " WHERE p.id=:pid" +
                " ORDER BY c.create_date DESC";
        return getSession().createQuery(hql)
                .setInteger("pid" , pid)
                .setMaxResults(pageSize)
                .setFirstResult(fromIndex)
                .setCacheable(true)
                .list();
    }

	@Override
	public void updateComment(int comment_id,int app_com_id) {
		
		String hql = "UPDATE Comment c SET c.flag=1 "
				+ ", c.append_comment.id=:app_com_id"
				+ " WHERE c.id=:comment_id";
		getSession().createQuery(hql)
				.setInteger("app_com_id", app_com_id)
				.setInteger("comment_id", comment_id)
				.executeUpdate();
	}
	
	@Override
	public List<Comment> getComment(int pid, int uid){
		String hql = "FROM Comment c"
				+ " JOIN FETCH c.user"
				+ " JOIN FETCH c.product"
				+ " JOIN FETCH c.comment_pic_List"
				+ " JOIN FETCH c.append_comment"
				+ " WHERE c.product.id=:pid"
				+ " AND c.user.id=:uid";
		return  getSession().createQuery(hql)
				.setInteger("pid", pid)
				.setInteger("uid", uid)
				.list();
	}
}
