package com.openking.oklog.utils;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/23/212:59 PM
 * des: 堆栈信息处理工具类
 */
public class OKStackTraceUtil {

    /**
     * 暴露给外部调用的方法
     *
     * @param stackTrace    传入堆栈信息
     * @param ignorePackage 忽略包名
     * @param maxDepth      最大深度
     * @return 返回处理结果
     */
    public static StackTraceElement[] getCroppedRealStackTrace(StackTraceElement[] stackTrace, String ignorePackage, int maxDepth) {
        return cropStaceTrace(getRealStackTrace(stackTrace, ignorePackage), maxDepth);
    }

    /**
     * 获取除需要忽略包名外的堆栈信息
     *
     * @param stackTrace    传入的堆栈信息
     * @param ignorePackage 要忽略的包名
     * @return 返回处理后的堆栈
     */
    private static StackTraceElement[] getRealStackTrace(StackTraceElement[] stackTrace, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTrace.length;
        String className;
        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTrace[i].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }

    /**
     * 裁剪堆栈信息
     *
     * @param callStack 传入需要裁剪的堆栈
     * @param maxDepth  最大打印的堆栈深度
     * @return 返回裁剪后的堆栈
     */
    private static StackTraceElement[] cropStaceTrace(StackTraceElement[] callStack, int maxDepth) {
        int realDepth = callStack.length;
        if (maxDepth > 0) {
            realDepth = Math.min(maxDepth, realDepth);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, 0, realStack, 0, realDepth);
        return realStack;
    }
}
