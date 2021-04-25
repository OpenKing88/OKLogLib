package com.openking.oklog;


import com.openking.oklog.format.OKStackTraceFormatter;
import com.openking.oklog.format.OKThreadFormatter;
import com.openking.oklog.printer.OKLogPrinter;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/2110:18 AM
 * des: 日志打印配置类
 */
public abstract class OKLogConfig {

    public static int MAX_LEN = 5120;
    static OKStackTraceFormatter OK_STACK_TRACE_FORMATTER = new OKStackTraceFormatter();
    static OKThreadFormatter OK_THREAD_FORMATTER = new OKThreadFormatter();

    public JsonParser injectJsonParser() {
        return null;
    }

    /**
     * 是否打印线程信息
     *
     * @return
     */
    public boolean includeThread() {
        return false;
    }

    /**
     * 打印堆栈的深度
     *
     * @return
     */
    public int stackTraceDepth() {
        return 5;
    }

    public OKLogPrinter[] printers(){
        return null;
    }

    /**
     * 返回默认的日志tag
     *
     * @return
     */
    public String getGlobalTag() {
        return "OKLog";
    }

    /**
     * 是否启用日志打印
     *
     * @return
     */
    public boolean enable() {
        return true;
    }

    /**
     * 提供给外部传入序列化的接口方法
     */
    public interface JsonParser {
        String toJson(Object src);
    }

}
