package com.halokonsultan.mobile.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.halokonsultan.mobile.databinding.FragmentOnboardingBinding
import com.halokonsultan.mobile.ui.onboarding.screens.BookingKonsultasiScreenFragment
import com.halokonsultan.mobile.ui.onboarding.screens.CariKonsultanScreenFragment
import com.halokonsultan.mobile.ui.onboarding.screens.ChatKonsultanScreenFragment

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingBinding.inflate(layoutInflater, container, false)

        val fragmentList = arrayListOf<Fragment>(
            CariKonsultanScreenFragment(),
            ChatKonsultanScreenFragment(),
            BookingKonsultasiScreenFragment()
        )

        val adapter = OnboardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.onboardingViewPager.adapter = adapter

        return binding.root
    }
}