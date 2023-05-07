package com.suspend.android.learntodrive.videoplayer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.question_simulation.IQuestionSimulationRepository
import com.suspend.android.learntodrive.di.provideYouTubeExtractor
import com.suspend.android.learntodrive.model.QuestionSimulationEntity
import com.suspend.android.learntodrive.model.VideoSimulation
import com.suspend.android.learntodrive.model.toVideoSimulation
import com.suspend.android.learntodrive.utils.extension.logError
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


private const val TAG = "VideoPlayerViewModel"

class VideoPlayerViewModel(
    private val questionSimulationRepository: IQuestionSimulationRepository
) : BaseViewModel() {
    var brightnessProgress: Int = 0

    /* private val _youtubeLink = MutableLiveData<String>()
     val youtubeLink: LiveData<String>
         get() = _youtubeLink*/

    private val _currentVideoSimulationItem = MutableLiveData<VideoSimulation>()
    val currentVideoSimulationItem: LiveData<VideoSimulation>
        get() = _currentVideoSimulationItem

    private val _currentPositionVideo =
        MutableLiveData<Int>() // vị trí video trong list bắt đầu từ 0
    val currentPositionVideo: LiveData<Int>
        get() = _currentPositionVideo

    private val _videoSimulationItemList = MutableLiveData<List<VideoSimulation>>()
    val videoSimulationItemList: LiveData<List<VideoSimulation>>
        get() = _videoSimulationItemList

    // private val videoLearnResultList = mutableListOf<VideoSimulation>()

    private var questionSimulationEntity: QuestionSimulationEntity? = null
    private val maxRetryGetStreamLink = 3
    private var retryGetStreamLink = maxRetryGetStreamLink


    fun updateQuestionToVideoLearnResultList(position: Int, videoSimulation: VideoSimulation) {
        _videoSimulationItemList.value?.let {
            val newList = it.toMutableList()
            newList[position] = videoSimulation
            _videoSimulationItemList.value = newList
        }

    }

    fun getCurrentVideoInLearnResultList(): VideoSimulation? {
        return if (_currentPositionVideo.value != null && _videoSimulationItemList.value != null)
            _videoSimulationItemList.value!![_currentPositionVideo.value!!]
        else null
    }

    fun getQuestionSimulationByID(context: Context, id: Int) {
        registerDisposable(
            executeTask(
                task = {
                    questionSimulationRepository.getQuestionSimulationByID(id)
                },
                onSuccess = {
                    retryGetStreamLink = maxRetryGetStreamLink
                    questionSimulationEntity = it
                    getUrlFromYouTube(context, it)
                },
                onError = {
                    error.value = "Đã xảy ra lỗi khi tải video thử lại sau"
                    logError(TAG, it.toString())
                }
            )
        )
    }

    fun moveToQuestionInPosition(position: Int) {
        if (position >= 0) {
            _currentPositionVideo.value = position
        }
    }

    fun getQuestionSimulationByType(type: Int) {
        registerDisposable(
            executeTask(
                task = {
                    questionSimulationRepository.getQuestionSimulationByType(type)
                },
                onSuccess = {
                    _videoSimulationItemList.value = it.map { it.toVideoSimulation("") }
                    moveToQuestionInPosition(0) // khoi tao video dau
                },
                onError = {
                    error.value = "Đã xảy ra lỗi khi tải video thử lại sau"
                    logError(TAG, it.toString())
                }
            )
        )
    }


    private fun getUrlFromYouTube(
        context: Context,
        questionSimulationEntity: QuestionSimulationEntity
    ) {
        viewModelScope.launch {
            provideYouTubeExtractor(context, onSuccess = { streamLink ->
                val videoSimulation = questionSimulationEntity.toVideoSimulation(streamLink)
                _currentVideoSimulationItem.value = videoSimulation
            }, onFailed = {
                runBlocking {

                    retryGetStreamLink--
                    if (retryGetStreamLink > 0) {
                        error.postValue("Đang thử lấy video lần ${maxRetryGetStreamLink - retryGetStreamLink}")
                        delay(2000)
                        getUrlFromYouTube(context, questionSimulationEntity)
                        logError(
                            TAG,
                            "Thử get link stream lần ${maxRetryGetStreamLink - retryGetStreamLink}"
                        )

                    } else {
                        error.postValue("Lỗi khi lấy link video thử lại sau")

                    }

                }
            }).extract("https://www.youtube.com/watch?v=${questionSimulationEntity.path}")
        }
    }


}