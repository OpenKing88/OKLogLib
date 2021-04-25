package com.openking.oklog.bean;

import java.text.SimpleDateFormat;
import java.util.Locale;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/213:51 PM
 * des: 视图打印器数据结构模型
 */
public class OKLogBean {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);
    public long timeMills;
    public int level;
    public String tag;
    public String log;

    public OKLogBean(long timeMills, int level, String tag, String log) {
        this.timeMills = timeMills;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    public String flattenedLog() {
        return getFlattened() + "\n" + log;
    }

    public String getFlattened() {
        return format(timeMills) + " | " + level + " | " + tag + " |: ";
    }

    public String format(long timeMills) {
        return sdf.format(timeMills);
    }
}
