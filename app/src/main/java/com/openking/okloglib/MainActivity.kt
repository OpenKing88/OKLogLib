package com.openking.okloglib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.openking.oklog.OKLog
import com.openking.oklog.OKLogConfig
import com.openking.oklog.OKLogManager
import com.openking.oklog.OKLogType
import com.openking.oklog.printer.OKViewPrinter

class MainActivity : AppCompatActivity() {

    private val viewPrinter by lazy { OKViewPrinter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OKLogManager.getInstance().addPrinter(viewPrinter)
        viewPrinter.provider.showFloatingView()
    }

    fun click(view: View) {
        //自定义Log配置
        OKLog.log(object : OKLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, OKLogType.D, "------", "dddddddddd")
        OKLog.e("printlogeeeeeeeee")
    }

    override fun onDestroy() {
        super.onDestroy()
        OKLogManager.getInstance().removePrinter(viewPrinter)
        viewPrinter.provider.closeFloatingView()
    }
}