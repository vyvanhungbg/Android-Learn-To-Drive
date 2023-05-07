package com.suspend.android.learntodrive.ui.trafficsign


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.suspend.android.learntodrive.base.BaseFragment
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.suspend.android.learntodrive.R
import com.suspend.android.learntodrive.databinding.FragmentTrafficSignBinding
import com.suspend.android.learntodrive.utils.extension.drawable
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class TrafficSignFragment :
    BaseFragment<FragmentTrafficSignBinding>(FragmentTrafficSignBinding::inflate) {
    override val viewModel by activityViewModel<TrafficSignViewModel>()

    private val callbackHandler = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    override fun initData() {

    }

    override fun initView() {

        setUpToolBarSearch()

        viewModel.trafficSignType.observe(viewLifecycleOwner) {
            val list = it.map { type -> SubTrafficSignFragment.newInstance(type.id, type.name) }
            activity?.let {
                val adapter = ViewPagerMainAdapter(
                    it.supportFragmentManager,
                    list
                )
                binding.viewPager.adapter = adapter
            }
            binding.viewPager.offscreenPageLimit = 1
            binding.smartTabLayout.setViewPager(binding.viewPager)
        }
        binding.imageViewExit.setOnClickListener {
            findNavController().popBackStack()
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callbackHandler)
    }

    override fun initEvent() {
        handleLiveSearch()
    }

    private fun setUpToolBarSearch() {
        binding.searchView.setVoiceSearch(true)
        binding.searchView.setAnimationDuration(1000)
        binding.searchView.setVoiceIcon(context?.drawable(R.drawable.ic_traffic_sign_black_24dp))
        binding.toolbar.inflateMenu(R.menu.menu_search_view)
        val menuItem: MenuItem = binding.toolbar.menu.findItem(R.id.action_search)
        binding.searchView.setMenuItem(menuItem)
    }


    private fun handleLiveSearch() {
        binding.searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.actionSearch(newText)
                return true
            }
        })
    }

    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWrd = matches[0]
                if (!TextUtils.isEmpty(searchWrd)) {
                    binding.searchView.setQuery(searchWrd, false)
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun isVoiceAvailable(): Boolean {

        val pm = context?.packageManager
        val activities = pm?.queryIntentActivities(
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0
        )
        return activities?.size != 0
    }


    fun onBackPressed() {
        if (binding.searchView.isSearchOpen()) {
            binding.searchView.closeSearch()
        } else {
            findNavController().popBackStack()
        }
    }
}