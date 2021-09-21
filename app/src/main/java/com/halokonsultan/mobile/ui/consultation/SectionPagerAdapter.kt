package com.halokonsultan.mobile.ui.consultation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.halokonsultan.mobile.utils.TAB_TITLES

class SectionPagerAdapter(activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        ConsultationListFragment(TAB_TITLES[position])
}