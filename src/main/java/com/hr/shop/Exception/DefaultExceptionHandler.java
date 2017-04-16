package com.hr.shop.Exception;

import com.hr.shop.Constant.Map_Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/24 15:28
 */
public class DefaultExceptionHandler implements HandlerExceptionResolver {
    private static Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,  Exception ex) {
        ModelAndView mv = new ModelAndView();
            /*  使用response返回    */
        response.setStatus(HttpStatus.OK.value()); //设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        response.setCharacterEncoding("UTF-8"); //避免乱码
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            response.getWriter().write("{\"status\":"+ Map_Msg.PARAM_IS_INVALID + ",\"msg\":\"" + ex.getMessage() + "\"}");
        } catch (IOException e) {
            log.error("与客户端通讯异常:"+ e.getMessage(), e);
        }

        log.debug("异常:" + ex.getMessage(), ex);
        return mv;
    }
}