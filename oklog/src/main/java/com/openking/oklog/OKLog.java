package com.openking.oklog;


import com.openking.oklog.printer.OKLogPrinter;
import com.openking.oklog.utils.OKStackTraceUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/2110:07 AM
 * des:
 */
public class OKLog {

    private static final String OK_LOG_PACKAGE;

    static {
        String className = OKLog.class.getName();
        OK_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

    public static void v(Object... contents) {
        log(OKLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(OKLogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(OKLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(OKLogType.D, tag, contents);
    }

    public static void i(Object... contents) {
        log(OKLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(OKLogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(OKLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(OKLogType.W, tag, contents);
    }

    public static void e(Object... contents) {
        log(OKLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(OKLogType.E, tag, contents);
    }

    public static void a(Object... contents) {
        log(OKLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(OKLogType.A, tag, contents);
    }

    public static void log(@OKLogType.TYPE int type, Object... contents) {
        log(type, OKLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@OKLogType.TYPE int type, @NotNull String tag, Object... contents) {
        log(OKLogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(@NotNull OKLogConfig config, @OKLogType.TYPE int type, @NotNull String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (config.includeThread()) {
            String threadInfo = OKLogConfig.OK_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            String stackTrace = OKLogConfig.OK_STACK_TRACE_FORMATTER.format(OKStackTraceUtil.getCroppedRealStackTrace(new Throwable().getStackTrace(), OK_LOG_PACKAGE, config.stackTraceDepth()));
            sb.append(stackTrace).append("\n");
        }
        String body = parseBody(contents, config);
        sb.append("OKPrintInfo:\n");
        sb.append("\t");
        sb.append(body);
        List<OKLogPrinter> printers = config.printers() != null ? Arrays.asList(config.printers()) : OKLogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        for (OKLogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());
        }
    }

    private static String parseBody(@NotNull Object[] contents, @NotNull OKLogConfig config) {
        StringBuilder sb = new StringBuilder();
        if (config.injectJsonParser() != null) {
            //如果配置了序列化器那么返回序列化后的数据
            return config.injectJsonParser().toJson(contents);
        }
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
