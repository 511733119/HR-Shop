package com.hr.shop.dto;

import java.util.Date;

/**
 * 接收订单支付结果类
 * @author hjc
 */
public class Pay_Result {

	private String body;//商品详情
	private Date create_time;//调起支付的时间
	private String name;//订单或商品名称
	private String out_trade_no;//Bmob系统的订单号
	private String transaction_id;//微信或支付宝的系统订单号
	private String pay_type;//支付方式
	private double total_fee;//订单总金额
	private String trade_state;//NOTPAY（未支付）或SUCCESS（已支付）
	
	public Pay_Result(){}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(double total_fee) {
		this.total_fee = total_fee;
	}

	public String getTrade_state() {
		return trade_state;
	}

	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
