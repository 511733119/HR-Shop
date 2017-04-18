package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.dto.Comment_Page;
import com.hr.shop.dto.Comment_dto;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.Comment;
import com.hr.shop.model.Sorder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hjc
 * 订单项Action
 */
@RestController
@RequestMapping("/api/sorders")
public class SorderAction extends BaseAction<Sorder> {
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET , produces = "application/json")
	@JsonView({View.son.class})
	public Map<String, Object> getSorder(@PathVariable("id") int id , int pid,int uid){
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();//存放数据Map
		Sorder sorder = sorderService.get(id);//获取该订单项对应的商品信息
		jsonMap.put("sorder", sorder);
		List<Comment> comment = commentService.getComment(pid, uid); //该用户是否已有过对该商品的评论
		//如果有
		if (!comment.isEmpty() && comment.size() > 0) {
			//获取之前的评论
			jsonMap.put("comment", comment.get(0));
		}
		return productService.successRespMap(respMap, Map_Msg.SUCCESS,jsonMap);
	}

}
