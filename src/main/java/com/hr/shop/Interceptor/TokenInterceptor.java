package com.hr.shop.Interceptor;

import com.google.gson.Gson;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Token 拦截器 用于检测Token是否过期Action
 * @author hjc
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private  TokenUtil tokenUtil;

    final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        logger.debug("-- TokenInterceptor --");
        // 判断token有效性
        String id = request.getParameter("id");
        int userid = 0;
        if(id != null){
            userid = Integer.parseInt(id);
        }
        String token = request.getParameter("token");
        if (tokenUtil.checkToken(userid, token)) {
            return true;
        }else{
            //token 过期
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", Map_Msg.ERROR);
            map.put("msg", Map_Msg.LOGIN_IS_EXPIRE);
            Gson gson = new Gson();
            String json = gson.toJson(map);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
            response.getWriter().flush();
            return false;
        }

    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}