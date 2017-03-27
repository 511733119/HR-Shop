package com.hr.shop.util;

import com.hr.shop.model.User;
import com.hr.shop.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author hjc
 * token 工具类
 */
@Component
public class TokenUtil {

    @Resource
    protected UserService userService;

    /**
     * 判断是否存在此token
     * @param token
     * @return
     */
    public boolean checkToken(int id , String token){
        User user = userService.checkToken(id ,token);
        if(user == null){
            return false;
        }
        return true;
    }
}
