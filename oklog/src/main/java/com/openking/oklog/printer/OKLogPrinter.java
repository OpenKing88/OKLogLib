package com.openking.oklog.printer;

import com.openking.oklog.OKLogConfig;

import org.jetbrains.annotations.NotNull;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/2110:55 AM
 * des: 打印日志的接口
 */
public interface OKLogPrinter {
    void print(@NotNull OKLogConfig config, int level, String tag, @NotNull String printStr);
}
