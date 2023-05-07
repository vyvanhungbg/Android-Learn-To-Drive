package com.suspend.android.learntodrive.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.suspend.android.learntodrive.utils.extension.showToast
import com.suspend.android.learntodrive.utils.extension.start

typealias Inflate<T> = (LayoutInflater) -> T

abstract class BaseFragment<VBinding : ViewBinding>(private val bindingInflater: Inflate<VBinding>) :
    Fragment() {

    private var _binding: VBinding? = null
    protected val binding: VBinding
        get() = _binding as VBinding
    protected abstract val viewModel: BaseViewModel
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = context?.let { Dialog(it) }
        viewModel.hasError.observe(viewLifecycleOwner) {
            context?.showToast(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                dialog?.start(false)
            } else {
                dialog?.dismiss()
            }
        }

        initView()
        initEvent()
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun initEvent()
}
