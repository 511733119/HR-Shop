package com.hr.shop.action;

import com.hr.shop.response.ResponseResult;
import com.hr.shop.response.UserResponseResult;
import com.hr.shop.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hjc
 * 公共Action
 * @param <T> 类名泛型
 */
@RestController
public class BaseAction<T>{

	protected Logger logger = LoggerFactory.getLogger(BaseAction.class);

	protected List<T> jsonList=null;

	protected String result;

	protected ResponseResult respRes ;

	protected UserResponseResult urespRes;

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

	public void setRespRes(ResponseResult respRes) {
		this.respRes = respRes;
	}

	public ResponseResult getRespRes() {
		return respRes;
	}

	public void setUrespRes(UserResponseResult urespRes) {
		this.urespRes = urespRes;
	}

	public UserResponseResult getUrespRes() {
		return urespRes;
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

	/**
	 * 请求异常
	 * @return
	 * @throws Exception
	 * String
	 */
	@RequestMapping(value = "/error_404", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String error_404() throws Exception {
		return "{\"status\":\"404\",\"msg\":\"找不到页面\"}";
	}

	@RequestMapping(value = "/error_400", produces = "text/html;charset=UTF-8")
	@ResponseBody
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
