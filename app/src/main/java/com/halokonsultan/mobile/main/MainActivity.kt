package com.halokonsultan.mobile.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.halokonsultan.mobile.databinding.ActivityMainBinding
import com.halokonsultan.mobile.login.LoginActivity
import com.halokonsultan.mobile.register.RegisterActivity
import com.halokonsultan.mobile.utils.*
import com.neovisionaries.ws.client.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}