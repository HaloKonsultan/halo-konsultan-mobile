package com.halokonsultan.mobile.ui.consultation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.halokonsultan.mobile.base.BaseFragment
import com.halokonsultan.mobile.databinding.FragmentConsultationBinding
import com.halokonsultan.mobile.utils.TAB_TITLES

class ConsultationFragment : BaseFragment<FragmentConsultationBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentConsultationBinding
        = FragmentConsultationBinding::inflate

    override fun setup() {
        if (activity != null) {
            val sectionPagerAdapter = SectionPagerAdapter(requireActivity())
            binding.viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }
}