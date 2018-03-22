package xd.flexible.ktmusic.ui.activity

import android.Manifest
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import flexible.xd.android_base.base.BaseActivity
import flexible.xd.android_base.utils.FileUtils
import flexible.xd.android_base.utils.LogUtils
import flexible.xd.android_base.utils.PermissionUtils
import flexible.xd.android_base.widget.RcvDividerLine
import kotlinx.android.synthetic.main.activity_lyric.*
import kotlinx.android.synthetic.main.activity_main.*
import xd.flexible.ktmusic.R
import xd.flexible.ktmusic.base.BaseMediaPlayer
import xd.flexible.ktmusic.base.Config
import xd.flexible.ktmusic.model.MusicBean
import xd.flexible.ktmusic.ui.adapter.MusicAdapter
import java.io.File
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : BaseActivity() {

    private lateinit var musicList: ArrayList<MusicBean>
    private lateinit var musicAdapter: MusicAdapter
    var ijkMediaPlayer = BaseMediaPlayer.getMediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(LyricActivity::class.java)
        initView()
        initData()
        initEvent()

    }

    private fun initEvent() {
        pcCircular.setOnClickListener {
            if (ijkMediaPlayer.isPlaying) {
                ijkMediaPlayer.pause()
                pcCircular.stop()
            } else {
                ijkMediaPlayer.start()
                pcCircular.play()
            }
        }
        val time: TimerTask = timerTask {
            if (ijkMediaPlayer.isPlaying) {
                runOnUiThread {
                    //duration 总时长 currentPosition 当前播放位置
                    if (ijkMediaPlayer.duration.toInt() != 0) {
                        pcCircular.progress = if ((ijkMediaPlayer.currentPosition * 100 / ijkMediaPlayer.duration).toInt() == 0) 1
                        else (ijkMediaPlayer.currentPosition * 100 / ijkMediaPlayer.duration).toInt()
                    }
                }
            }
        }
        val timer = Timer()
        timer.schedule(time, 0, 1000)
        var isPause = false
        var pausePos = -1
        musicAdapter.setOnClickListener(object : MusicAdapter.OnItemClickListener {
            override fun onClick(v: View, pos: Int) {
                if (ijkMediaPlayer.isPlaying) {
                    if (musicList[pos].playing) {//暂停
                        pcCircular.stop()
                        ijkMediaPlayer.pause()
                        pausePos = pos
                    } else {//切换
                        pcCircular.play()
                        pcCircular.progress = 0
                        tvName.text = musicList[pos].name
                        tvInfo.text = musicList[pos].author
                        ijkMediaPlayer.reset()
                        ijkMediaPlayer.dataSource = musicList[pos].path
                        ijkMediaPlayer.prepareAsync()
                        ijkMediaPlayer.start()
                        musicAdapter.isPlaying(pos)
                        pausePos = -1
                    }
                } else {
                    if (isPause && pos == pausePos) {//继续
                        pcCircular.play()
                        ijkMediaPlayer.start()
                    } else {//切换重新开始
                        pcCircular.play()
                        pcCircular.progress = 0
                        ijkMediaPlayer.reset()
                        ijkMediaPlayer.dataSource = musicList[pos].path
                        ijkMediaPlayer.prepareAsync()
                        tvName.text = musicList[pos].name
                        tvInfo.text = musicList[pos].author
                        musicAdapter.isPlaying(pos)
                        isPause = true
                        pausePos = -1
                    }
                }
                musicAdapter.notifyDataSetChanged()
            }

        })
    }

    private fun initData() {
        musicList = arrayListOf<MusicBean>()
        musicAdapter = MusicAdapter(musicList, this)
        rvMusic.adapter = musicAdapter
        findLocalMusic()
    }

    private fun findLocalMusic() {
        val file = File(Config.LOCAL_MUSIC)
        val fileTreeWalk = file.walk()
        fileTreeWalk.filter {
            it.isFile
        }.forEach {
            val allName = it.nameWithoutExtension.split("-");
            val music = MusicBean()
            music.name = allName[1].trim()
            music.size = FileUtils.getFileSize(it)
            music.author = allName[0].trim()
            music.path = it.absolutePath
            musicList.add(music)
        }
        musicAdapter.notifyDataSetChanged()


    }

    private fun initView() {
        PermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).request()
        rvMusic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val line = RcvDividerLine(RcvDividerLine.VERTICAL)
        line.setColor(resources.getColor(R.color.divider))
        line.setSize(2)
        rvMusic.addItemDecoration(line)
    }

    override fun onStop() {
        super.onStop()
        ijkMediaPlayer.stop()
    }
}
