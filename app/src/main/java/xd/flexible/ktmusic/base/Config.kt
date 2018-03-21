package xd.flexible.ktmusic.base

import android.os.Environment


/**
 * Created by Flexible on 2018/3/3 0003.
 */
interface Config {
    companion object {
        val LOCAL_MUSIC: String
            get() = Environment.getExternalStorageDirectory().getPath() + "/flexible/Music"
    }
}
