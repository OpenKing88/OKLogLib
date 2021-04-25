package com.openking.oklog;


import com.openking.oklog.printer.OKLogPrinter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/2110:19 AM
 * des: 日志管理类，持有logConfig
 */
public class OKLogManager {
    private final OKLogConfig config;
    private static OKLogManager instance;
    private final List<OKLogPrinter> printers = new ArrayList<>();

    private OKLogManager(OKLogConfig config, OKLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static OKLogManager getInstance() {
        return instance;
    }

    public static void init(@NotNull OKLogConfig config, OKLogPrinter... printers) {
        instance = new OKLogManager(config, printers);
    }

    public OKLogConfig getConfig() {
        return config;
    }

    //获取打印器列表
    public List<OKLogPrinter> getPrinters() {
        return printers;
    }

    //动态添加打印器
    public void addPrinter(OKLogPrinter printer) {
        printers.add(printer);
    }

    //移除打印器
    public void removePrinter(OKLogPrinter printer) {
        printers.remove(printer);
    }
}
