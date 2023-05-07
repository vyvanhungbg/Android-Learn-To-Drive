package com.suspend.android.learntodrive.ui.trafficsign

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerMainAdapter(
    fragmentManager: FragmentManager,
    private val fragments: List<SubTrafficSignFragment>
) : FragmentStatePagerAdapter(fragmentManager) {


    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragments[position].title
    }
}
