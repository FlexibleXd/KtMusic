package xd.flexible.ktmusic.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import xd.flexible.ktmusic.R
import kotlinx.android.synthetic.main.view_lyric.view.*
import xd.flexible.ktmusic.model.LyricBean
import xd.flexible.ktmusic.ui.adapter.LyricAdapter

/**
 * Created by Flexible on 2018/3/21 0021.
 */
class LyricView : FrameLayout {

    private lateinit var lyricAdapter: LyricAdapter

    var lyricText = arrayListOf<LyricBean>()


//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        super.onLayout(true, left, top, right, bottom)
//    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val count = childCount
//        for (i in 0 until count) {
//            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec)
//        }
//    }

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
//         = LayoutInflater.from(context).inflate(R.layout.view_lyric, null, false)
//        addView(view, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        with(view) {
            rvLyric.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            lyricAdapter = LyricAdapter(lyricText, context)
            rvLyric.adapter = lyricAdapter
        }
    }
}