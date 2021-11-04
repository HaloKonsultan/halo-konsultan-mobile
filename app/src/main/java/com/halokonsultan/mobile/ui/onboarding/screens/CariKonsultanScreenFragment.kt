package com.halokonsultan.mobile.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.FragmentOnboardingCariBinding

class CariKonsultanScreenFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingCariBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingCariBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.onboardingViewPager)

        binding.btnPrev.isEnabled = false

        binding.btnNext.setOnClickListener{
            viewPager?.currentItem = 1
        }
    }
}