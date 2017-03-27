package com.hr.shop.action;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author hjc
 * 通用页面跳转Action
 */
@Controller("sendAction")
public class SendAction extends ActionSupport {

	public String execute(){
		return "send";
	}
}
