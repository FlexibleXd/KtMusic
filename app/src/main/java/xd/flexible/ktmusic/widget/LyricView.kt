package xd.flexible.ktmusic.widget

import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import xd.flexible.ktmusic.R
import kotlinx.android.synthetic.main.view_lyric.view.*
import xd.flexible.ktmusic.R.id.rvLyric
import xd.flexible.ktmusic.model.LyricBean
import xd.flexible.ktmusic.ui.adapter.LyricAdapter
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Flexible on 2018/3/21 0021.
 */
class LyricView : FrameLayout {

    private lateinit var lyricAdapter: LyricAdapter

    var lyricText = arrayListOf<LyricBean>()

    var dataFormat = SimpleDateFormat("mm:ss.SS")

    var nowTime: String = "00:00.00"
        set(value) {
            field = dataFormat.format(Date(value.toLong()))
            lyricScroll()
        }

    var nowPos: Int = 0

    fun notify(value: List<LyricBean>) {
        lyricText.clear()
        lyricText.addAll(value)
        lyricAdapter.notifyDataSetChanged()
        invalidate()
    }

    constructor(context: Context) : super(context) {
        initView()
    }


    constructor(context: Context, mAttributeSet: AttributeSet) : super(context, mAttributeSet) {
        initView()
    }


    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }


    private fun initView() {
        val view = View.inflate(context, R.layout.view_lyric, this)
        with(view) {
            rvLyric.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            lyricAdapter = LyricAdapter(lyricText, context)
            rvLyric.adapter = lyricAdapter

//

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
            }
            else -> {
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * 歌词滑动
     */
    private fun lyricScroll() {
        val lyric = lyricText[nowPos + 1]
        val posTime = dataFormat.parse(lyric.time)
        val nowTime = dataFormat.parse(nowTime)
        if (nowTime.after(posTime)) {
            (rvLyric.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(++nowPos, rvLyric.height / 2)
            lyricAdapter.nowPos = nowPos
            lyricAdapter.notifyDataSetChanged()
        }
    }

}