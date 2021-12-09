package com.halokonsultan.mobile.ui.main

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.BaseActivity
import com.halokonsultan.mobile.ui.chat.ChatFragment
import com.halokonsultan.mobile.ui.consultation.ConsultationFragment
import com.halokonsultan.mobile.databinding.ActivityMainBinding
import com.halokonsultan.mobile.ui.home.HomeFragment
import com.halokonsultan.mobile.ui.profile.ProfileFragment
import com.halokonsultan.mobile.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        = ActivityMainBinding::inflate

    override fun setup() {
        setOnNavigationListener()
    }

    private fun setOnNavigationListener() {
        val homeFragment = HomeFragment()
        val chatFragment = ChatFragment()
        val consultationFragment = ConsultationFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miChat -> setCurrentFragment(chatFragment)
                R.id.miConsultation -> setCurrentFragment(consultationFragment)
                R.id.miProfile -> setCurrentFragment(profileFragment)
            }

            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            setStatusBarColor(fragment)
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun setStatusBarColor(fragment: Fragment) {
        if (fragment is HomeFragment) {
            window.statusBarColor = getColorResource(R.color.primary_blue)
            Utils.setStatusBarLightText(window, true)
        } else {
            window.statusBarColor = getColorResource(R.color.white)
            Utils.setStatusBarLightText(window, false)
        }
    }
}