package com.openking.oklog.format;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/2110:59 AM
 * des: 线程格式化器，格式化线程日志信息,它实现了日志格式化接口
 */
public class OKThreadFormatter implements OKLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "\t\nThread:" + data.getName();
    }
}
