package com.openking.oklog.format;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/2111:01 AM
 * des: 堆栈信息格式化器，用户格式化堆栈信息打印，实现格式化器接口
 */
public class OKStackTraceFormatter implements OKLogFormatter<StackTraceElement[]> {
    @Override
    public String format(StackTraceElement[] data) {
        StringBuilder sb = new StringBuilder(128);
        if (data == null || data.length == 0) {
            return null;
        } else if (data.length == 1) {
            return "\t " + data[0].toString();
        } else {
            for (int i = 0, len = data.length; i < len; i++) {
                if (i == 0) {
                    sb.append("\t\nstackTrace:\n");
                }
                if (i != len - 1) {
                    if (i == 0) {
                        sb.append("\t┌ ");
                    } else
                        sb.append("\t│ ");
                    sb.append(data[i].toString());
                    sb.append("\n");
                } else {
                    sb.append("\t└ ");
                    sb.append(data[i].toString());
                }
            }
            return sb.toString();
        }
    }
}
