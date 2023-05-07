package com.suspend.android.learntodrive.ui.tips

import androidx.navigation.fragment.findNavController
import com.suspend.android.learntodrive.base.BaseFragment
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.powerMenu
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.databinding.FragmentTipsBinding
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.drawable
import com.suspend.android.learntodrive.utils.extension.logError
import org.koin.androidx.viewmodel.ext.android.viewModel


class TipsFragment : BaseFragment<FragmentTipsBinding>(FragmentTipsBinding::inflate) {
    override val viewModel by viewModel<TipsViewModel>()

    private val moreMenu by powerMenu<MoreMenuTipFactory>()

    override fun initData() {

    }

    override fun initView() {
        binding.webView.loadUrl(Constant.PATH.TIPS_600)
        setUpMenu()
        binding.imageViewExit.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initEvent() {

    }

    private fun setUpMenu(){
        binding.imageViewSubMenu.setOnClickListener {
            moreMenu?.showAsAnchorRightBottom(it)
            moreMenu?.setOnMenuItemClickListener(object : OnMenuItemClickListener<PowerMenuItem> {
                override fun onItemClick(position: Int, item: PowerMenuItem?) {
                    when (position) {
                        0 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_600)

                        }
                        1 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_A1)
                        }
                        2 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_A2)
                        }
                        3 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_A3)
                        }
                        4 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_A4)
                        }
                        5 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_B1)
                        }
                        6 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_B2)
                        }
                        7 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_C)
                        }
                        8 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_DEF)
                        }
                        9 -> {
                            binding.webView.loadUrl(Constant.PATH.TIPS_EXAM_DRIVE)
                        }

                    }
                    moreMenu?.selectedPosition = position
                }

            })
            if (moreMenu?.isShowing == true) {
                binding.imageViewSubMenu.setImageDrawable(context?.drawable(R.drawable.ic_menu_close_24dp))
            }
        }
        moreMenu?.setOnDismissedListener {

            logError(message = "Dimess")
            binding.imageViewSubMenu.setImageDrawable(context?.drawable(R.drawable.ic_baseline_menu_24))
        }
    }

}