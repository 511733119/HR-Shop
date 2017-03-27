package com.hr.shop.response;

import com.alibaba.fastjson.JSON;

/**
 * 统一处理格式类
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Date: 2017/3/25 17:19
 */
public class ResponseResult {

    private String status;
    private Object msg;

    public ResponseResult() {

    }

    public ResponseResult(String status , Object msg){
        this.status = status;
        this.msg = msg;
    }

    public Object getMsg() {
        return msg;
    }
    public void setMsg(Object msg) {
        this.msg = msg;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String toString(){
        if(null == this.msg){
            this.setMsg(new Object());
        }
        return JSON.toJSONString(this);
    }
}