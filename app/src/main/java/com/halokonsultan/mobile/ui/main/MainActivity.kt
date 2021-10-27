package com.halokonsultan.mobile.ui.main

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.ui.chat.ChatFragment
import com.halokonsultan.mobile.ui.consultation.ConsultationFragment
import com.halokonsultan.mobile.databinding.ActivityMainBinding
import com.halokonsultan.mobile.ui.home.HomeFragment
import com.halokonsultan.mobile.ui.profile.ProfileFragment
import com.halokonsultan.mobile.utils.Utils
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
        askPermissions()

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
            window.statusBarColor = resources.getColor(R.color.primary_blue)
            Utils.setStatusBarLightText(window, true)
        } else {
            window.statusBarColor = resources.getColor(R.color.white)
            Utils.setStatusBarLightText(window, false)
        }
    }

    private fun askPermissions(){
        askPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ){

        }.onDeclined{ e ->
            if (e.hasDenied()){
                e.denied.forEach { _ ->
                    AlertDialog.Builder(this)
                        .setMessage("Please Accept Our Permission")
                        .setPositiveButton("Yes"){ _, _ ->
                            e.askAgain()
                        }
                        .setNegativeButton("No"){ dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }

            if (e.hasForeverDenied()){
                e.foreverDenied.forEach { _ ->
                    e.goToSettings()
                }
            }
        }
    }
}