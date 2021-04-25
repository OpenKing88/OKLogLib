package com.openking.okloglib

import android.app.Application
import com.google.gson.Gson
import com.openking.oklog.OKLogConfig
import com.openking.oklog.OKLogManager
import com.openking.oklog.printer.OKConsolePrinter
import com.openking.oklog.printer.OKLogFilePrinter

/***
 *
 * 去好好学习吧,又看源码,看你妹呀！
 * author: openKing
 * e-mail: 362608496@qq.com
 * date: 4/25/2111:45 AM
 * des:
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        OKLogManager.init(
            object : OKLogConfig() {
                override fun injectJsonParser(): JsonParser {
                    return JsonParser {
                        Gson().toJson(it)
                    }
                }

                override fun includeThread(): Boolean {
                    return true
                }

                override fun stackTraceDepth(): Int {
                    return 5
                }

                override fun getGlobalTag(): String {
                    return "customTag"
                }

                //日志打印的开关
                override fun enable(): Boolean {
                    return true
                }
            },
            OKConsolePrinter(),
            OKLogFilePrinter.getInstance(applicationContext.cacheDir.absolutePath, 0)
        )
    }
}