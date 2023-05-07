package com.suspend.android.learntodrive.ui.result

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.util.LruCache
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseViewModel
import com.suspend.android.learntodrive.data.repository.test.ITestRepository
import com.suspend.android.learntodrive.model.ResultInfo
import com.suspend.android.learntodrive.model.ResultTest
import com.suspend.android.learntodrive.model.TestEntity
import com.suspend.android.learntodrive.model.toTestEntity
import com.suspend.android.learntodrive.utils.extension.getCurrentLicenseName


private const val TAG = "ResultViewModel"

class ResultViewModel(
    private val testRepository: ITestRepository,
    private val sharedPreferencesSettings: SharedPreferences,
) : BaseViewModel() {

    private val _listStatistics = MutableLiveData<List<ResultInfo>>()
    val listStatistics: LiveData<List<ResultInfo>>
        get() = _listStatistics


    private val _testEntityInserted = MutableLiveData<TestEntity>(null)
    val testEntityInserted: LiveData<TestEntity>
        get() = _testEntityInserted

    fun getListStatistic(item: ResultTest) {
        val list = mutableListOf<ResultInfo>()
        val total = item.correctAnswer + item.wrongAnswer + item.ignoreAnswer
        list.add(ResultInfo(total.toString(), "Câu hỏi", R.color.bg_layout_home_required_answer))
        list.add(
            ResultInfo(
                item.ignoreAnswer.toString(),
                "Chưa trả lời",
                R.color.bg_layout_home_traffic_signs
            )
        )
        list.add(
            ResultInfo(
                item.wrongAnswer.toString(),
                "Trả lời sai",
                R.color.bg_layout_home_random
            )
        )
        list.add(
            ResultInfo(
                item.correctAnswer.toString(),
                "Trả lời đúng",
                R.color.bg_layout_home_theoretical
            )
        )
        _listStatistics.value = list
    }

    fun insertTest(testEntity: ResultTest) {
        registerDisposable(
            executeTask(
                task = {
                    testRepository.insert(testEntity.toTestEntity(sharedPreferencesSettings.getCurrentLicenseName()))
                },
                onSuccess = {
                    /*getEntityInsertByRowID(it)*/
                    Log.e(TAG, "insertTest: ")
                },
                onError = {
                    Log.e(TAG, "failed: ${it.toString()}")
                },
                loadingInvisible = false
            )
        )
    }

    fun convertToTestEntity(testEntity: ResultTest){
        _testEntityInserted.value = testEntity.toTestEntity(sharedPreferencesSettings.getCurrentLicenseName())
    }

    private fun getEntityInsertByRowID(rowID: Long) {
        registerDisposable(
            executeTask(
                task = {
                    testRepository.getByRowID(rowID)
                },
                onSuccess = {
                    _testEntityInserted.value = it
                },
                onError = {
                    Log.e(TAG, "failed: ${it.toString()}")
                },
                loadingInvisible = false
            )
        )
    }

    fun getScreenshotFromRecyclerView(view: RecyclerView): Bitmap? {
        val adapter = view.adapter
        var bigBitmap: Bitmap? = null
        if (adapter != null) {
            val size: Int = adapter.getItemCount()
            var height = 0
            val paint = Paint()
            var iHeight = 0
            val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

            // Use 1/8th of the available memory for this memory cache.
            val cacheSize = maxMemory / 8
            val bitmaCache: LruCache<String, Bitmap> = LruCache(cacheSize)
            for (i in 0 until size) {
                val holder: RecyclerView.ViewHolder =
                    adapter.createViewHolder(view, adapter.getItemViewType(i))
                adapter.onBindViewHolder(holder, i)
                holder.itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                holder.itemView.layout(
                    0,
                    0,
                    holder.itemView.getMeasuredWidth(),
                    holder.itemView.getMeasuredHeight()
                )
                holder.itemView.setDrawingCacheEnabled(true)
                holder.itemView.buildDrawingCache()
                val drawingCache: Bitmap = holder.itemView.getDrawingCache()
                if (drawingCache != null) {
                    bitmaCache.put(i.toString(), drawingCache)
                }
                //                holder.itemView.setDrawingCacheEnabled(false);
//                holder.itemView.destroyDrawingCache();
                height += holder.itemView.getMeasuredHeight()
            }
            bigBitmap =
                Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888)
            val bigCanvas = Canvas(bigBitmap)
            bigCanvas.drawColor(Color.WHITE)
            for (i in 0 until size) {
                val bitmap: Bitmap = bitmaCache.get(i.toString())
                bigCanvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
                iHeight += bitmap.height
                bitmap.recycle()
            }
        }
        return bigBitmap
    }

}
