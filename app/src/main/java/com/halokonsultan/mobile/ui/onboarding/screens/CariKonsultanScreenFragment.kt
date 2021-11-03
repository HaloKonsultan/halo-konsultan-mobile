package com.halokonsultan.mobile.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.FragmentCariKonsultanScreenBinding

class CariKonsultanScreenFragment : Fragment() {

    private lateinit var binding: FragmentCariKonsultanScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCariKonsultanScreenBinding.inflate(layoutInflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onboardingViewPager)

        binding.btnNext.setOnClickListener{
            viewPager?.currentItem = 1
        }

        return binding.root
    }
}