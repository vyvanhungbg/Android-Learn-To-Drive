package com.suspend.android.learntodrive.ui.home

import android.content.SharedPreferences
import androidx.navigation.fragment.findNavController
import com.suspend.android.learntodrive.base.BaseFragment
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.adapter.SliderAdapter
import com.suspend.android.learntodrive.databinding.FragmentHomeBinding
import com.suspend.android.learntodrive.di.SHARED_PREFERENCES_TYPE
import com.suspend.android.learntodrive.model.ItemHome
import com.suspend.android.learntodrive.ui.test.TYPE
import com.suspend.android.learntodrive.utils.extension.getCurrentLicenseName
import com.suspend.android.learntodrive.utils.extension.navigateSafe
import com.suspend.android.learntodrive.utils.extension.showToast
import com.suspend.android.learntodrive.utils.extension.toPercent
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel by viewModel<HomeViewModel>()

    private val listAdapterHomeItem by lazy { ListAdapterHomeItem(::onClickItem) }

    private val sliderAdapter by lazy { SliderAdapter() }

    private val sharedPreferences =  get<SharedPreferences>(named(SHARED_PREFERENCES_TYPE.SETTINGS))

    override fun initData() {
        // impl
        context?.let { viewModel.getItemHome(it) }
        viewModel.getSlider()
    }

    override fun initView() {
        viewModel.getAllQuestionsStatistic()
        binding.recyclerViewHome.adapter = listAdapterHomeItem
        viewModel.itemHome.observe(viewLifecycleOwner) {
            listAdapterHomeItem.submitList(it)
        }
        binding.sliderView.apply {
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
            startAutoCycle()

        }
        binding.sliderView.setSliderAdapter(sliderAdapter)
        viewModel.slides.observe(viewLifecycleOwner) {
            sliderAdapter.submitList(it)
        }
        viewModel.tests.observe(viewLifecycleOwner) {
            binding.textViewPercentPass.text = viewModel.calcPercentPassExams().toPercent()
            binding.textViewTested.text = viewModel.calcTotalTests().toString()
            binding.textViewNameCurrentLicense.text = sharedPreferences.getCurrentLicenseName()
        }
    }

    override fun initEvent() {
        // impl
        binding.textViewNameCurrentLicense.setOnClickListener {
            context?.showToast(R.string.mess_action_click_current_license)
        }
    }

    private fun onClickItem(item: ItemHome) {

        when (item) {
            ItemHome.RANDOM -> {
                findNavController().navigateSafe(
                    HomeFragmentDirections.actionNavigationHomeToNavigationPrepareTest(
                        bundleType = TYPE.TEST
                    )
                )

            }

            ItemHome.EXAM_SET ->{
                findNavController().navigateSafe(HomeFragmentDirections.actionNavigationHomeToNavigationListExamSetFragment())
            }

            ItemHome.THEORETICAL -> {
               /* findNavController().navigateSafe(
                    HomeFragmentDirections.actionNavigationHomeToNavigationPrepareTest(
                        TYPE.LEARN
                    )
                )*/
                findNavController().navigateSafe(HomeFragmentDirections.actionNavigationHomeToNavigationListQuestionType())
            }

            ItemHome.TRAFFIC_SIGNS -> {
                findNavController().navigateSafe(HomeFragmentDirections.actionNavigationHomeToNavigationTrafficSignFragment())
            }

            ItemHome.WRONG_ANSWER -> {
                findNavController().navigateSafe(HomeFragmentDirections.actionNavigationHomeToNavigationWrongAnswer())
            }

            ItemHome.DIE_QUESTION -> {
                findNavController().navigateSafe(HomeFragmentDirections.actionNavigationHomeToNavigationQuestionDie())
            }

            ItemHome.TIPS_ANSWER -> {
                findNavController().navigateSafe(HomeFragmentDirections.actionNavigationHomeToNavigationTips())
            }
            else -> {}
        }
    }

}
