package com.halokonsultan.mobile.ui.consultation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.halokonsultan.mobile.databinding.FragmentConsultationBinding
import com.halokonsultan.mobile.utils.TAB_TITLES

class ConsultationFragment : Fragment() {

    private lateinit var binding: FragmentConsultationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConsultationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val sectionPagerAdapter = SectionPagerAdapter(requireActivity())
            binding.viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }
}