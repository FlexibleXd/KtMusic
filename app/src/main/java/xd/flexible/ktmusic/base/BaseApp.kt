package xd.flexible.ktmusic.base

import android.app.Application
import android.content.Context
import flexible.xd.android_base.utils.Utils
import java.io.File

/**
 * Created by Flexible on 2018/3/3 0003.
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        initDir()
        Utils.init(this)
    }

    private fun initDir() {
        val dir = File(Config.LOCAL_MUSIC)
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }


    companion object {
        lateinit var ctx: Context

        fun getAppContext(): Context {
            return ctx
        }
    }

}