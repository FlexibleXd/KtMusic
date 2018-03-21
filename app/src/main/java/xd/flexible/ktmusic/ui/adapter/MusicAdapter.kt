package xd.flexible.ktmusic.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import xd.flexible.ktmusic.R
import xd.flexible.ktmusic.base.BaseApp
import xd.flexible.ktmusic.model.MusicBean
import kotlinx.android.synthetic.main.item_music_list.view.*

/**
 * Created by Flexible on 2018/3/3 0003.
 */
class MusicAdapter(var data: List<MusicBean>, var ctx: Context = BaseApp.getAppContext()) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var mHolder = holder as MusicVH
        val music = data.get(position)

        with(mHolder.itemView) {
            tvName.text = music.name
            tvInfo.text = music.author
            if (music.playing) {
                ivHorn.visibility = VISIBLE
            } else {
                ivHorn.visibility = GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(ctx).inflate(R.layout.item_music_list, parent, false);
        return MusicVH(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun isPlaying(pos: Int) {
        for (i in data.indices) {
            data[i].playing = i == pos
        }
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

    inner class MusicVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(itemView, position)
                }
            }
        }
    }


}