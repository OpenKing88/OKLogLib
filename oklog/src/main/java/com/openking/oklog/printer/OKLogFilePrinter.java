package com.openking.oklog.printer;


import com.openking.oklog.OKLogConfig;
import com.openking.oklog.bean.OKLogBean;
import com.openking.oklog.utils.OKFileUtils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/25/2110:02 AM
 * des: 日志文件打印
 */
public class OKLogFilePrinter implements OKLogPrinter {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final String logPath;//日志文件存储路径
    private final long retentionTime;//日志文件存储时长
    private final PrintWorker worker;

    private static OKLogFilePrinter instance;

    private OKLogFilePrinter(String logPath, long retentionTime) {
        this.logPath = logPath + "/OKLog";
        this.retentionTime = retentionTime;
        this.worker = new PrintWorker();
        cleanExpiredLog();
    }

    public static synchronized OKLogFilePrinter getInstance(String logPath, long retentionTime) {
        if (instance == null) {
            instance = new OKLogFilePrinter(logPath, retentionTime);
        }
        return instance;
    }

    @Override
    public void print(@NotNull OKLogConfig config, int level, String tag, @NotNull String printStr) {
        long mills = System.currentTimeMillis();
        if (!worker.isRunning()) {
            worker.start();
        }
        worker.put(new OKLogBean(mills, level, tag, printStr));
    }

    /**
     * 打印日志消息线程
     */
    private class PrintWorker implements Runnable {

        //存放日志的队列
        private final BlockingDeque<OKLogBean> logBeans = new LinkedBlockingDeque<>();

        private volatile boolean running;

        /**
         * 将日志加入打印队列
         *
         * @param bean 需要打印的日志
         */
        void put(OKLogBean bean) {
            try {
                logBeans.put(bean);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 判断当前线程是否在运行
         *
         * @return true 运行中
         */
        boolean isRunning() {
            synchronized (this) {
                return running;
            }
        }

        /**
         * 启动工作线程
         */
        void start() {
            synchronized (this) {
                EXECUTOR.execute(this);
                running = true;
            }
        }

        @Override
        public void run() {
            OKLogBean log;
            try {
                while (true) {
                    log = logBeans.take();
                    doPrint(log);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                synchronized (this) {
                    running = false;
                }
            }

        }
    }

    private void doPrint(OKLogBean log) {
        OKFileUtils.writeFileFromString(logPath + "/" + genFileName() + ".log", log.flattenedLog(), true);
    }

    /**
     * 清除过期日志
     */
    private void cleanExpiredLog() {
        if (retentionTime <= 0) {
            return;
        }
        long currentMills = System.currentTimeMillis();
        OKFileUtils.deleteFilesInDirWithFilter(logPath, file -> currentMills - file.lastModified() > retentionTime);
    }

    private String genFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }
}
