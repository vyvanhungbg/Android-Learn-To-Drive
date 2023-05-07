package com.suspend.android.learntodrive.ui.setting

import android.content.Context
import android.util.Log
import android.view.WindowManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.databinding.FragmentSettingBinding
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.logError
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import com.xw.repo.BubbleSeekBar
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "SettingFragment"
class SettingFragment :
    BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    override val viewModel by viewModel<SettingViewModel>()

    override fun initData() {
        // impl
    }

    override fun initView() {



        binding.sbTextSize.onProgressChangedListener =
            object : BubbleSeekBar.OnProgressChangedListenerAdapter() {
                override fun onProgressChanged(
                    bubbleSeekBar: BubbleSeekBar?,
                    progress: Int,
                    progressFloat: Float,
                    fromUser: Boolean
                ) {
                    super.onProgressChanged(bubbleSeekBar, progress, progressFloat, fromUser)

                    val scaleText = progressFloat / 20.0F
                    binding.titleSbTextSize.textSize = scaleText * 16
                    Log.e(TAG, "onProgressChanged: ${scaleText}", )
                    adjustFontScale(context!!, scaleText)
                }

                override fun getProgressOnActionUp(
                    bubbleSeekBar: BubbleSeekBar?,
                    progress: Int,
                    progressFloat: Float
                ) {
                    super.getProgressOnActionUp(bubbleSeekBar, progress, progressFloat)

                }

                override fun getProgressOnFinally(
                    bubbleSeekBar: BubbleSeekBar?,
                    progress: Int,
                    progressFloat: Float,
                    fromUser: Boolean
                ) {
                    super.getProgressOnFinally(bubbleSeekBar, progress, progressFloat, fromUser)

                }
            }

    }

    override fun initEvent() {
        // impl
    }



    fun adjustFontScale(context: Context, scale: Float) {
        val configuration = context.resources.configuration
        configuration.fontScale = scale
        val metrics = resources.displayMetrics
        val windowManager = getSystemService(
            context,
            Context.WINDOW_SERVICE::class.java
        ) as? WindowManager?
        windowManager?.let {
            it.defaultDisplay?.getMetrics(metrics)
        }
        metrics.scaledDensity = configuration.fontScale * metrics.density
        context.resources.updateConfiguration(configuration, metrics)
    }

}