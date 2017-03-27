package com.hr.shop.response;

/**
 * UserAction响应格式统一处理类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/25 21:33
 */
public class Ex_UserResponseResult extends UserResponseResult{
    private String username;
    private String isNewUser;

    public Ex_UserResponseResult() {
    }

    public Ex_UserResponseResult(String status, Object msg,String token ,int userid, String phone , String username, String isNewUser){
        super(status,msg , token, userid , phone);
        this.username = username;
        this.isNewUser = isNewUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setIsNewUser(String isNewUser) {
        this.isNewUser = isNewUser;
    }

    public String getIsNewUser() {
        return isNewUser;
    }

}
