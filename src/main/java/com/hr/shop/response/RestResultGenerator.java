package com.hr.shop.response;

import org.springframework.stereotype.Component;

/**
 * 统一响应格式处理类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/25 17:22
 */
@Component
public class RestResultGenerator {

    public static ResponseResult genResult(String status,Object msg){
        return new ResponseResult(status , msg);
    }

    public static UserResponseResult genUserResult(String status,Object msg,String token,int userid,String phone){
       return new UserResponseResult(status,msg,token,userid,phone);
    }

    public static Ex_UserResponseResult genEx_UserResult(String status,Object msg,String token,int userid,String phone, String username, String isNewUser){
        return new Ex_UserResponseResult(status,msg,token,userid,phone,username,isNewUser);
    }
}