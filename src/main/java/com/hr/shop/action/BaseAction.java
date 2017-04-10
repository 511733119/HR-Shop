package com.hr.shop.action;

import com.hr.shop.model.Comment;
import com.hr.shop.service.*;
import com.hr.shop.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hjc
 * 公共Action
 * @param <T> 类名泛型
 */
@RestController
public class BaseAction<T>{

	protected Logger logger = LoggerFactory.getLogger(BaseAction.class);

	protected List<T> jsonList;

	protected List<Comment> app_com_jsonList;

	protected String result;

	protected ResponseEntity<?> entity ;

	protected Map<String,Object> respMap;

	@Resource
	protected CategoryService categoryService;
	@Resource
	protected AccountService accountService;
	@Resource
	protected ProductService productService;
	@Resource
	protected ProtypeService protypeService;
	@Resource
	protected ForderService forderService;
	@Resource
	protected SorderService sorderService;
	@Resource
	protected CartService cartService;
	@Resource
	protected UserService userService;
	@Resource
	protected CommentService commentService;
	@Resource
	protected Append_CommentService append_CommentService;
	@Resource
	protected FileUploadUtil fileUploadUtil;

	public void setApp_com_jsonList(List<Comment> app_com_jsonList) {
		this.app_com_jsonList = app_com_jsonList;
	}

	public List<Comment> getApp_com_jsonList() {
		return app_com_jsonList;
	}

	public void setJsonList(List<T> jsonList) {
		this.jsonList = jsonList;
	}

	public List<T> getJsonList() {
		return jsonList;
	}

	public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

	public void setEntity(ResponseEntity<?> entity) {
		this.entity = entity;
	}

	public ResponseEntity<?> getEntity() {
		return entity;
	}

	public void setRespMap(Map<String, Object> respMap) {
		this.respMap = respMap;
	}

	public Map<String, Object> getRespMap() {
		return respMap;
	}

	/**
	 * 请求异常
	 * @return
	 * @throws Exception
	 * String
	 */
	@RequestMapping(value = "/error_404", produces = "text/html;charset=UTF-8")
	public String error_404() throws Exception {
		return "{\"status\":\"404\",\"msg\":\"找不到页面\"}";
	}

	@RequestMapping(value = "/error_400", produces = "text/html;charset=UTF-8")
	public String error_400() throws Exception {
		return "{\"status\":\"400\",\"msg\":\"请求参数非法\"}";
	}
	/**
	 * 服务器异常
	 * @return
	 * String
	 */
	@RequestMapping(value ="/error_500", produces = "text/html;charset=UTF-8")
	public String error_500() {
 		return "{\"status\":\"500\",\"msg\":\"服务器处理失败\"}";
	}

}
