package xd.flexible.ktmusic.ui.activity

import android.os.Bundle
import flexible.xd.android_base.base.ToolBarActivity
import flexible.xd.android_base.utils.FileUtils
import flexible.xd.android_base.utils.LogUtils
import xd.flexible.ktmusic.R
import kotlinx.android.synthetic.main.activity_lyric.*
import kotlinx.android.synthetic.main.view_lyric.view.*
import xd.flexible.ktmusic.base.Config
import xd.flexible.ktmusic.model.LyricBean
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


/**
 * Created by Flexible on 2018/3/21 0021.
 */
class LyricActivity : ToolBarActivity() {
//    private lateinit var lyricAdapter: LyricAdapter

//    private val lyricText = arrayListOf<LyricBean>()
//        set(value) {
//            field = value
//            lyricAdapter.notifyDataSetChanged()
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyric)
        initView()
        initData()
        initEvent()
    }

    private fun initEvent() {
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
//        rvLyric.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        lyricAdapter = LyricAdapter(lyricText, this)
//        rvLyric.adapter = lyricAdapter
    }
}