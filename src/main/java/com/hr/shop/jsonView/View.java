package com.hr.shop.jsonView;

/**
 * HR-Shop
 * Created by hjc
 * User: hjc
 * Timestamp: 2017/3/23 23:15
 */
public class View {
    public interface summary {}//基本

    public interface son extends summary{}//过滤product中的protypeSet

    public interface son2 extends summary{} //过滤protypeSet中的product

    public interface commentExcept extends summary{} //过滤user中的password,phone,token,avatar,create_date,update_date

    public interface exceptPwd extends summary{} //过滤user中的password

}