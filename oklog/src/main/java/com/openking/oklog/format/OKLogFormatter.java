package com.openking.oklog.format;

 /***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/2110:57 AM
 * des: 日志格式化接口,将任何类型的数据格式化成字符串便于日志打印
 */
public interface OKLogFormatter<T> {

    String format(T data);
}
