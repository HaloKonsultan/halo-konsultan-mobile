package com.halokonsultan.mobile.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.ui.chat.ChatFragment
import com.halokonsultan.mobile.ui.consultation.ConsultationFragment
import com.halokonsultan.mobile.databinding.ActivityMainBinding
import com.halokonsultan.mobile.ui.home.HomeFragment
import com.halokonsultan.mobile.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            replace(R.id.flFragment, fragment)
            commit()
        }
}