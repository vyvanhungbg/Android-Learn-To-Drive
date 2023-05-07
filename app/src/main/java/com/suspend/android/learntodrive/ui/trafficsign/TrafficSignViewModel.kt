package com.suspend.android.learntodrive.ui.trafficsign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.trafficsigns.ITrafficSignRepository
import com.suspend.android.learntodrive.model.TrafficSignEntity
import com.suspend.android.learntodrive.model.TrafficSignTypeEntity
import com.suspend.android.learntodrive.utils.extension.ignoreFastAction
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.extension.normalizeVietnamese
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

private const val TAG = "TrafficSignViewModel"

class TrafficSignViewModel(private val trafficSignRepository: ITrafficSignRepository) :
    BaseViewModel() {

    private val _trafficSign = MutableLiveData<List<TrafficSignEntity>>()
    private val trafficSign: LiveData<List<TrafficSignEntity>>
        get() = _trafficSign

    private val _trafficSignFilter = MediatorLiveData<List<TrafficSignEntity>>()
    val trafficSignFilter: LiveData<List<TrafficSignEntity>>
        get() = _trafficSignFilter

    private val _trafficSignType = MutableLiveData<List<TrafficSignTypeEntity>>()
    val trafficSignType: LiveData<List<TrafficSignTypeEntity>>
        get() = _trafficSignType

    private val subjectObservable = PublishSubject.create<String?>()

    init {
        _trafficSignFilter.addSource(_trafficSign) {
            _trafficSignFilter.value = it
        }
        getTrafficSignType()
        getTrafficSign()
        handleSearch()
    }

    private fun getTrafficSign() {
        registerDisposable(
            executeTask(
                task = { trafficSignRepository.getAllTrafficSigns() },
                onSuccess = {
                    _trafficSign.value = it
                },
                onError = { logError(TAG, it.message) }
            )
        )
    }

    fun actionSearch(queryText: String?) {
        subjectObservable.onNext(queryText)
    }

    private fun handleSearch(){
        registerDisposable(
            subjectObservable
                .subscribeOn(Schedulers.computation())
                .ignoreFastAction()
                .distinctUntilChanged()
                .doOnNext { logError(TAG, it) }
                .map { name ->
                    return@map if (name.isNullOrEmpty()) {
                        trafficSign.value ?: mutableListOf()
                    } else {
                        trafficSign.value
                            ?.filter {
                                it.name.contains(name, true)
                                        || it.image.contains(name,true)
                                        || it.description?.contains(name, true) ?: false
                                        || it.name.normalizeVietnamese().contains(name, true)
                            }
                            ?: mutableListOf()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        logError(TAG, "in")
                        _trafficSignFilter.value = it
                    },
                    {
                        logError(TAG, it.message)
                    }
                )
        )
    }

    private fun getTrafficSignType() {
        registerDisposable(
            executeTask(
                task = { trafficSignRepository.getAllTypeTrafficSigns() },
                onSuccess = {
                    _trafficSignType.value = it
                },
                onError = { logError(TAG, it.message) }
            )
        )
    }

}