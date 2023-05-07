package com.suspend.android.learntodrive.videoplayer

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.text.Subtitle
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.util.Util
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseActivity
import com.suspend.android.learntodrive.databinding.ActivityVideoPlayerBinding
import com.suspend.android.learntodrive.databinding.MenuLayoutListSimulationQuestionBinding
import com.suspend.android.learntodrive.model.VideoSimulation
import com.suspend.android.learntodrive.utils.extension.color
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.extension.showToast
import com.suspend.android.learntodrive.utils.extension.toBrightness
import com.suspend.android.learntodrive.utils.extension.toPercentageString
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Float.min
import javax.xml.transform.Source


private const val TAG = "VideoPlayerActivity"


class VideoPlayerActivity :
    BaseActivity<ActivityVideoPlayerBinding>(ActivityVideoPlayerBinding::inflate), Player.Listener {

    private val viewModel by viewModel<VideoPlayerViewModel>()

    private var mediaPlayer: ExoPlayer? = null
    private val trackSelector: TrackSelector? = null
    private var layoutZoom: RelativeLayout? = null
    private var scaleDetector: ScaleGestureDetector? = null
    private var seekBarBrightness: SeekBar? = null
    private val settingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ -> }

    private val buttonSpace by lazy { findViewById<ImageView>(R.id.image_view_space_bar) }
    private val buttonExit by lazy { findViewById<ImageView>(R.id.image_view_exit_in_video_player) }
    private val playerControlView by lazy { findViewById<PlayerControlView?>(com.google.android.exoplayer2.ui.R.id.exo_controller) }
    private val seekBarMarkedFlag by lazy { findViewById<SeekBar?>(R.id.seek_bar_marked_flag) }
    private val textViewPositionQuestion by lazy { findViewById<TextView?>(R.id.text_view_position_question) }

    private val timeBar by lazy { findViewById<DefaultTimeBar?>(R.id.exo_progress) }
    private val imageViewQuestionList by lazy { findViewById<ImageView?>(R.id.image_view_question_list) }
    private var isMarkedFlag = false

    private val safeArgs by navArgs<VideoPlayerActivityArgs>()

    private val listAdapterQuestionSimulation by lazy {
        ListAdapterVideoSimulationItem { _, position ->
            viewModel.moveToQuestionInPosition(position)
            menu.closeMenu()
        }
    }

    private val bindingMenu by lazy { MenuLayoutListSimulationQuestionBinding.inflate(layoutInflater) }

    private val menu by lazy {
        SlidingRootNavBuilder(this)
            .withMenuView(bindingMenu.root)
            .withContentClickableWhenMenuOpened(false)
            .withDragDistance(300)
            .withRootViewElevation(25)
            .withRootViewScale(1F)
            .withRootViewYTranslation(4).inject()
    }

    override fun initData() {
        mediaPlayer = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
                // Tạo ra các nguồn phát video bằng ExtractorMediaSource
                /* val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this)
                 val videoSource1: MediaSource =
                     ProgressiveMediaSource.Factory(dataSourceFactory)
                         .createMediaSource(MediaItem.fromUri(videoSimulationItem.streamURL))
                 val videoSource2: MediaSource =
                     ProgressiveMediaSource.Factory(dataSourceFactory)
                         .createMediaSource(MediaItem.fromUri(videoSimulationItem.streamURL))
                 val videoSource3: MediaSource =
                     ProgressiveMediaSource.Factory(dataSourceFactory)
                         .createMediaSource(MediaItem.fromUri(videoSimulationItem.streamURL))

                 // Tạo một ConcatenatingMediaSource và thêm các nguồn phát video vào
                 val concatenatedSource =
                     ConcatenatingMediaSource(videoSource1, videoSource2, videoSource3)*/

            }
        // không đổi vị trí dòng code
        viewModel.getQuestionSimulationByType(safeArgs.typeQuestion)
        setUpMenu()
        scaleDetector = ScaleGestureDetector(this, ScaleDetector())

    }

    override fun handleEvent() {
        buttonSpace.setOnClickListener {

            // check chưa bấm space thì cho phép bấm
            if (isMarkedFlag.not()) {
                val position = mediaPlayer?.currentPosition ?: 0L
                val totalDuration = mediaPlayer?.duration ?: 1
//                hiện cờ đánh dấu người dùng chọn
                seekBarMarkedFlag.progress = (position.toDouble() / totalDuration * 100).toInt()
                seekBarMarkedFlag.thumbTintList = ColorStateList.valueOf(color(R.color.color_red))
                isMarkedFlag = true

                val myCore = viewModel.currentVideoSimulationItem.value?.calCore(position) ?: 0
                showToast("Điểm của bạn là $myCore")
                Log.e(TAG, "Bấm lúc : $position")

                // ghi lai câu trả lời
                val currentVideoInResultList = viewModel.getCurrentVideoInLearnResultList()
                currentVideoInResultList?.let {
                    val currentPosition = viewModel.currentPositionVideo.value
                    currentPosition?.let { _positionIndex ->
                        viewModel.updateQuestionToVideoLearnResultList(
                            _positionIndex,
                            currentVideoInResultList.copy(
                                isSelected = isMarkedFlag,
                                currentScore = myCore,
                                currentTimeSelected = position,
                                totalTimeVideo = totalDuration
                            )
                        )
                    }

                }

            } else {
                showToast(R.string.mess_one_time_click_to_marked_flag)
            }

        }

        buttonExit.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun bindData() {

        viewModel.currentPositionVideo.observe(this) {
            listAdapterQuestionSimulation.updateCurrentItemSelected(it)
            if (it < viewModel.videoSimulationItemList.value?.size ?: 0) {
                val idCurrentItem = viewModel.videoSimulationItemList.value?.get(it)
                if (idCurrentItem != null) {
                    viewModel.getQuestionSimulationByID(context = this, id = idCurrentItem.id)
                } else {
                    showToast("Lấy video thất bại !. Có lỗi xảy ra khi lấy video này")
                }
            } else {
                showToast("Đã là video cuối của bài học")
            }

        }

        viewModel.hasError.observe(this) {
            showToast(it)
        }
        viewModel.currentVideoSimulationItem.observe(this) {
            isMarkedFlag = it.isSelected
            seekBarMarkedFlag.thumbTintList =
                ColorStateList.valueOf(color(android.R.color.transparent))
            textViewPositionQuestion.text = "Câu ${(viewModel.currentPositionVideo.value ?: 0) + 1}"
            if (isMarkedFlag) {
                val position = it.currentTimeSelected
                val totalDuration = it.totalTimeVideo
//                hiện cờ đánh dấu người dùng chọn
                seekBarMarkedFlag.progress = (position.toDouble() / totalDuration * 100).toInt()
                seekBarMarkedFlag.thumbTintList = ColorStateList.valueOf(color(R.color.color_red))
                showToast("Bạn đã trả lời câu này !")
            }
            initializePlayer(it)
        }
        binding.videoView.apply {
            setOnTouchListener { _, motionEvent ->
                scaleDetector?.onTouchEvent(motionEvent)
                super.onTouchEvent(motionEvent)
            }
        }
        layoutZoom = findViewById(R.id.layout_exo_zoom)
        seekBarBrightness = findViewById(R.id.seek_bar_brightness)
        initializeBrightness()

        seekBarMarkedFlag.setOnTouchListener { _, _ ->
            true // Tắt khả năng vuốt SeekBar
        }

    }

    private fun setUpMenu() {
        bindingMenu.recyclerViewQuestionSimulation.adapter = listAdapterQuestionSimulation

        viewModel.videoSimulationItemList.observe(this) {
            if (it.isNotEmpty()) {
                listAdapterQuestionSimulation.submitList(it)
            } else {
                showToast(getString(R.string.mess_error_when_get_list_simulation_question))
            }
        }
        imageViewQuestionList.setOnClickListener {
            menu.openMenu(true)
        }
    }

    private fun initializeBrightness() {
        viewModel.brightnessProgress =
            Settings.System.getInt(
                applicationContext.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                0
            ) * 100 / 255
        seekBarBrightness?.progress = viewModel.brightnessProgress
        changeBrightnessSettings()

    }

    override fun onDestroy() {
        mediaPlayer?.removeListener(this)
        super.onDestroy()
    }

    private fun initializePlayer(videoSimulationItem: VideoSimulation) {

        mediaPlayer?.setMediaItem(MediaItem.fromUri(videoSimulationItem.streamURL))
        // exoPlayer.setMediaSource(concatenatedSource)
        mediaPlayer?.prepare()
        mediaPlayer?.addListener(this)


// Tạo nguồn phát video


// Thiết lập nguồn phát cho ExoPlayer


        val groupMarkedScore = mutableListOf<Long>(videoSimulationItem.startTime * 1000L)
        val groupMarkedScoreChecked = mutableListOf<Boolean>(false)
        playerControlView.setExtraAdGroupMarkers(
            groupMarkedScore.toLongArray(),
            groupMarkedScoreChecked.toBooleanArray()
        )


        timeBar?.setAdGroupTimesMs(
            groupMarkedScore.toLongArray(),
            groupMarkedScoreChecked.toBooleanArray(),
            groupMarkedScore.size
        )

    }

    private fun hasPermissionsToWriteSettings(context: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.System.canWrite(context)
        } else {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_SETTINGS
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun changeBrightnessSettings() {
        seekBarBrightness?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            private var progress = 0
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progress = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}


            override fun onStopTrackingTouch(p0: SeekBar?) {

                if (hasPermissionsToWriteSettings(this@VideoPlayerActivity)) {
                    changeBrightness(progress)
                } else {
                    //set seekbar to previous state
                    seekBarBrightness?.progress =
                        viewModel.brightnessProgress

                    val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                            .also { it.data = Uri.parse("package:" + packageName) }
                    } else {
                        Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS)
                            .also { it.data = Uri.parse("package:" + packageName) }
                    }
                    settingsLauncher.launch(intent)
                }
            }
        })
    }

    private fun changeBrightness(progress: Int) {
        val brightness = progress.toBrightness()
        Settings.System.putInt(
            applicationContext?.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        Settings.System.putInt(
            applicationContext?.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS, brightness
        )
        // viewModel.streamingStates.brightnessProgress = progress
    }

    private fun playMedia(source: Source?, subtitle: Subtitle?) {


        mediaPlayer?.let {
            it.clearMediaItems()

            it.prepare()
            // it.seekTo(viewModel.streamingStates.position)
            it.playWhenReady = true
        }
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, binding.videoView).let { controller ->
            controller.show(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun releasePlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            viewModel.currentVideoSimulationItem.value?.let {
                initializePlayer(it)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((Util.SDK_INT <= 23 || mediaPlayer == null)) {
            viewModel.currentVideoSimulationItem.value?.let {
                initializePlayer(it)
            }
        }
    }


    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
        showSystemUi()
    }


    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        if (playbackState == Player.STATE_ENDED) {
            // Video đã phát xong
            val currentVideoInResultList = viewModel.getCurrentVideoInLearnResultList()
            currentVideoInResultList?.let {
                val currentPosition = viewModel.currentPositionVideo.value
                currentPosition?.let { _positionIndex ->
                    viewModel.updateQuestionToVideoLearnResultList(
                        _positionIndex,
                        currentVideoInResultList.copy(
                            isSelected = true,
                            currentScore = 0,
                            currentTimeSelected = 0,
                            totalTimeVideo = 0
                        )
                    )
                }

            }
            val currentPosition = viewModel.currentPositionVideo.value ?: 0
            val maxPosition = viewModel.videoSimulationItemList.value?.size ?: 0
            if (currentPosition < maxPosition) {
                viewModel.moveToQuestionInPosition(currentPosition + 1)
            }else{
                //showToast(getString(R.string.mess_notification_last_question))
            }
        }
    }

    inner class ScaleDetector : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        val DEFAULT_SCALE_FACTOR = 1f
        val MIN_SCALE_FACTOR = 1f
        val MAX_SCALE_FACTOR = 6f
        var scaleFactor = DEFAULT_SCALE_FACTOR

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            try {
                scaleFactor *= detector.scaleFactor
                scaleFactor = maxOf(MIN_SCALE_FACTOR, min(scaleFactor, MAX_SCALE_FACTOR))
                layoutZoom?.apply {
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                findViewById<TextView>(R.id.text_zooming_percentage)?.apply {
                    visibility = View.VISIBLE
                    text = (scaleFactor * 100).toInt().toPercentageString()
                    visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                logError(TAG, e.message)
            }
            //  binding.videoView.text_
            return super.onScale(detector)
        }
    }
}