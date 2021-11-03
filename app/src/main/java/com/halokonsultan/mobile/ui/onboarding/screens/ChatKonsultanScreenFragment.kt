package com.halokonsultan.mobile.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.FragmentChatKonsultanScreenBinding

class ChatKonsultanScreenFragment : Fragment() {

    private lateinit var binding: FragmentChatKonsultanScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatKonsultanScreenBinding.inflate(layoutInflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onboardingViewPager)

        binding.btnNext.setOnClickListener{
            viewPager?.currentItem = 2
        }

        return binding.root
    }
}