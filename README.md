# OKLogLib
一款日志打印工具封装库，可用于开发调试和日志追踪！

使用方式：
  参考 app 模块中有详细代码
  
  1.在你的application中初始化OKLog
  
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
  注：OKLogConfig()可选择实现方法，JsonParser->替换成你自己对应的Json序列化工具，如果不实现，默认不序列化，非字符串的日志无法序列化打印
                                includeThread->是否打印当前线程，默认不打印
                                stackTraceDepth->打印堆栈信息的深度，默认5，如果为0则不打印
                                getGlobalTag->自定义log的Tag
                                enable->日志打印开关
      OKConsolePrinter：控制台打印器
      OKLogFilePrinter：日志文件打印器
      
  2.实现Android页面显示日志信息
  
  class MainActivity : AppCompatActivity() {

    //创建视图打印器对象
    private val viewPrinter by lazy { OKViewPrinter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //添加视图打印器
        OKLogManager.getInstance().addPrinter(viewPrinter)
        //打开视图打印器入口
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
        //移除视图打印器
        OKLogManager.getInstance().removePrinter(viewPrinter)
        viewPrinter.provider.closeFloatingView()
    }
}
