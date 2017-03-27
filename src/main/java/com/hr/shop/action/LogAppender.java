package com.hr.shop.action;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * @author hjc
 * 重写Appender，实现log4j不同级别的日志分别存储在不同日志文件中
 */
public class LogAppender extends DailyRollingFileAppender {

    @Override
    public boolean isAsSevereAsThreshold(Priority priority) {
        //只判断是否相等，而不判断优先级
        return this.getThreshold().equals(priority);
    }
}
