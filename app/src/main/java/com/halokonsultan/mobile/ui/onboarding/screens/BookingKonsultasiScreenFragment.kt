package com.halokonsultan.mobile.ui.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.FragmentOnboardingBookingBinding
import com.halokonsultan.mobile.ui.auth.LandingActivity
import com.halokonsultan.mobile.ui.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingKonsultasiScreenFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBookingBinding
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBookingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onboardingViewPager)

        binding.btnPrev.setOnClickListener {
            viewPager?.currentItem = 1
        }

        binding.btnMasuk.setOnClickListener {
            viewModel.finishOnBoarding()
            val intent = Intent(context, LandingActivity::class.java)
            startActivity(intent)
        }
    }
}