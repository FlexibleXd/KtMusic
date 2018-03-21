package xd.flexible.ktmusic.base

import tv.danmaku.ijk.media.player.IjkMediaPlayer
import xd.flexible.ktmusic.widget.media.Settings

/**
 * Created by Flexible on 2018/3/3 0003.
 */
object BaseMediaPlayer {
    var ijk = IjkMediaPlayer()

    fun getMediaPlayer(): IjkMediaPlayer {
        val mSettings = Settings(BaseApp.getAppContext())
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")
        return ijk
    }
}