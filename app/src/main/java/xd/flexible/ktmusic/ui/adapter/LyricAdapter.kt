package xd.flexible.ktmusic.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_lyric.view.*
import xd.flexible.ktmusic.R
import xd.flexible.ktmusic.base.BaseApp
import xd.flexible.ktmusic.model.LyricBean

/**
 * Created by Flexible on 2018/3/3 0003.
 */
class LyricAdapter(var data: List<LyricBean>, private var ctx: Context = BaseApp.getAppContext()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var mHolder = holder as LyricVH
        val music = data[position]
        with(mHolder.itemView) {
            tvLyric.text = music.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(ctx).inflate(R.layout.item_lyric, parent, false);
        return LyricVH(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    /**
     * 点击事件的回调
     */
    interface OnItemClickListener {
        fun onClick(v: View, pos: Int)
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class LyricVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(itemView, position)
                }
            }
        }
    }
}