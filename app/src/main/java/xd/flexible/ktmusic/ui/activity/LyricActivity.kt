package xd.flexible.ktmusic.ui.activity

import android.os.Bundle
import flexible.xd.android_base.base.ToolBarActivity
import flexible.xd.android_base.utils.FileUtils
import flexible.xd.android_base.utils.LogUtils
import xd.flexible.ktmusic.R
import kotlinx.android.synthetic.main.activity_lyric.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_lyric.view.*
import xd.flexible.ktmusic.R.id.lvLyric
import xd.flexible.ktmusic.base.BaseMediaPlayer
import xd.flexible.ktmusic.base.Config
import xd.flexible.ktmusic.model.LyricBean
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*
import kotlin.concurrent.timerTask


/**
 * Created by Flexible on 2018/3/21 0021.
 */
class LyricActivity : ToolBarActivity() {
    var ijkMediaPlayer = BaseMediaPlayer.getMediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyric)
        initView()
        initData()
        initEvent()
    }

    private fun initEvent() {
        val time: TimerTask = timerTask {
            if (ijkMediaPlayer.isPlaying) {
                runOnUiThread {
                    LogUtils.LOGE("main", " ijkMediaPlayer.currentPosition.toString()" +  ijkMediaPlayer.currentPosition.toString())
                    lvLyric.nowTime = ijkMediaPlayer.currentPosition.toString()

                }
            }
        }
        val timer = Timer()
        timer.schedule(time, 0, 1000)
    }

    private fun initData() {
        val lyric = FileUtils.getFileByPath(Config.LOCAL_MUSIC + "/苏诗丁 - 血腥爱情故事.lrc")
        val lyricList = arrayListOf<LyricBean>()
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader(lyric))
            var tempString: String? = null
            // 一次读入一行，直到读入null为文件结束
            tempString = reader.readLine()
            while (tempString != null) {
                val split = tempString!!.split("]")
                val lyric = LyricBean()
                lyric.time = split[0].substring(1, split[0].length)
                lyric.text = split[1]
                lyricList.add(lyric)
                tempString = reader.readLine()
            }
//            lyricText.addAll(lyricList)
//            lyricAdapter.notifyDataSetChanged()
            lvLyric.notify(lyricList)
            reader!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader!!.close()
                } catch (e1: IOException) {
                }

            }
        }
    }

    private fun initView() {

//        ijkMediaPlayer.reset()
        ijkMediaPlayer.dataSource = Config.LOCAL_MUSIC + "/苏诗丁 - 血腥爱情故事.mp3"
        ijkMediaPlayer.prepareAsync()
        ijkMediaPlayer.start()
    }
}