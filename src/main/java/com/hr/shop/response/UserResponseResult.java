package com.hr.shop.response;

/**
 * UserAction响应格式统一处理类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/25 21:33
 */
public class UserResponseResult extends ResponseResult{
    private String token;
    private int userid;
    private String phone;

    public UserResponseResult() {
    }

    public UserResponseResult(String status, Object msg,String token ,int userid, String phone){
        super(status,msg);
        this.token = token;
        this.userid = userid;
        this.phone = phone;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

}
