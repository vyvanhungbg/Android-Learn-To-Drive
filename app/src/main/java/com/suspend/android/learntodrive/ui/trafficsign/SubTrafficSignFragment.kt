package com.suspend.android.learntodrive.ui.trafficsign

import android.app.Dialog
import com.suspend.android.learntodrive.base.BaseFragment
import com.suspend.android.learntodrive.databinding.FragmentSubTrafficSignBinding
import com.suspend.android.learntodrive.model.TrafficSignEntity
import com.suspend.android.learntodrive.utils.constant.Constant
import com.suspend.android.learntodrive.utils.extension.viewImage
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SubTrafficSignFragment :
    BaseFragment<FragmentSubTrafficSignBinding>(FragmentSubTrafficSignBinding::inflate) {
    override val viewModel by activityViewModel<TrafficSignViewModel>()

    private val listAdapterTrafficSigns by lazy { ListAdapterTrafficSigns(::imageOnClick) }

    private var typeTrafficSign = 1

    private var _title = ""
    val title get() = _title

    private var dialog: Dialog? = null

    override fun initData() {
        context?.let {
            dialog = Dialog(it)
        }
    }

    override fun initView() {
        binding.recyclerViewTrafficSign.adapter = listAdapterTrafficSigns
        viewModel.trafficSignFilter.observe(viewLifecycleOwner) {
            listAdapterTrafficSigns.submitList(it.filter { item -> item.type == typeTrafficSign })
        }
    }

    override fun initEvent() {

    }

    private fun imageOnClick(item: TrafficSignEntity) {
        dialog?.viewImage(Constant.PATH.TRAFFIC_SIGN + item.path)
    }

    companion object {
        @JvmStatic
        fun newInstance(type: Int, title: String): SubTrafficSignFragment {
            val fragment = SubTrafficSignFragment()
            fragment._title = title
            fragment.typeTrafficSign = type
            return fragment
        }

    }
}