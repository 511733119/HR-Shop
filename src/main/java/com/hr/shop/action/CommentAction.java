package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.Append_Comment;
import com.hr.shop.model.Append_Comment_Pic;
import com.hr.shop.model.Comment;
import com.hr.shop.model.Comment_Pic;
import com.hr.shop.model.Product;
import com.hr.shop.model.User;

import org.aspectj.bridge.MessageWriter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 评论Action类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/31 20:52
 */
@RestController
@RequestMapping(value = "/api/comments")
public class CommentAction extends BaseAction<Comment> {

	/**
	 * 获得某件商品的评论
	 * @param pid
	 * @param pageNum
	 * @return
	 */
    @RequestMapping(value = "/{pid}" , method = RequestMethod.GET ,produces="application/json;charset=UTF-8")
    @JsonView({View.son.class})
    public Map<String,Object> getProductComment(@PathVariable("pid") int pid, int pageNum ){
        if(pid <=0 || pageNum <= 0){
            return productService.errorRespMap(respMap, Map_Msg.PARAM_IS_INVALID);
        }
        app_com_jsonList = commentService.getProductComment(pid , pageNum , 10);
        return productService.successRespMap(respMap, Map_Msg.SUCCESS,app_com_jsonList);
    }
    
    /**
     * 保存评论
     * @param star 评分星星数
     * @param comment 评论文字
     * @param files 上传的图片数组
     * @param sid 订单项id 
     * @return
     */
    @RequestMapping(value = "/comment" , method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public Map<String, Object> saveComment(int pid , int uid,int star, String comment, int sid,@RequestParam("file") MultipartFile[] file){
    	
    	if(pid <= 0 || uid <= 0 || star <= 0){
    		return productService.errorRespMap(respMap, Map_Msg.PARAM_IS_INVALID);
    	}
    	//中文转码
    	try {
		    comment = new String(comment.getBytes("iso-8859-1"),"UTF-8");//对中文进行解码，先转为iso-8859-1字节流，再转为utf8字符流
    	} catch (UnsupportedEncodingException e) {
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
    	commentService.saveComment(star, comment, file, pid, uid, sid);//保存评论
        return productService.successRespMap(respMap, Map_Msg.SUCCESS,"");
    }
    
    /**
     * 保存追加评论
     * @param comment_id //评论对象id
     * @param append_Comment 追加评论对象
     * @param sid 订单项id 
     * @return
     */
    @RequestMapping(value = "/append_comment" , method = RequestMethod.POST,produces = "application/json;charset=UTF-8" )
    public Map<String, Object> saveAppend_Comment(int comment_id, int pid, int uid, String append_Comment,int sid, @RequestParam("file") MultipartFile[] file){
    	
    	if(pid <= 0 || uid <= 0 || sid <= 0){
    		return productService.errorRespMap(respMap, Map_Msg.PARAM_IS_INVALID);
    	}
    	try {
		    append_Comment = new String(append_Comment.getBytes("iso-8859-1"),"UTF-8");//对中文进行解码，先转为iso-8859-1字节流，再转为utf8字符流
    	} catch (UnsupportedEncodingException e) {
    		logger.error("字符编码转换错误");
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
    	append_CommentService.saveAppend_Comment(append_Comment, pid, uid, file, comment_id, sid);//保存追评
    	
    	return productService.successRespMap(respMap, Map_Msg.SUCCESS, "");
    }
    
}
