package com.hr.shop.dao;

import com.hr.shop.model.Comment;

import java.util.List;

/**
 * 评论接口
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/31 20:30
 */
public interface CommentDao extends BaseDao<Comment> {

    /**
     * 获取某件商品下的评论
     * @param pid
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Comment> getProductComment(int pid , int pageNum , int pageSize);
    /**
	 * 更新flag, 关联追加评论对象
	 * @param comment_id 评论id
	 * @param app_com_id 追加评论对象id
	 */
    public void updateComment(int comment_id,int app_com_id);
    
    /**
     * 追加评论时界面显示上一次的评论内容
     * @param pid
     * @param uid
     * @return
     */
    public List<Comment> getComment(int pid, int uid);
}
