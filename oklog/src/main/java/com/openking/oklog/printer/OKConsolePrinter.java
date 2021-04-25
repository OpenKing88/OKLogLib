package com.openking.oklog.printer;

import android.util.Log;

import com.openking.oklog.OKLogConfig;

import org.jetbrains.annotations.NotNull;

import static com.openking.oklog.OKLogConfig.MAX_LEN;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/2111:38 AM
 * des: 控制台打印器，实现了OKLogPrinter接口
 */
public class OKConsolePrinter implements OKLogPrinter {
    @Override
    public void print(@NotNull OKLogConfig config, int level, String tag, @NotNull String printStr) {
        int len = printStr.length();//需要打印字符的长度
        int countOfSub = len / MAX_LEN;//一共需要打印几行
        if (countOfSub > 0) {
            int index = 0;//记录打印的长度
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, printStr.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            //当字符长度不能整除时，需要将剩余的字符打印出来
            if (index != len) {
                Log.println(level, tag, printStr.substring(index, len));
            }
        } else {
            //不足1行时，全部打印
            Log.println(level, tag, printStr);
        }
    }
}
