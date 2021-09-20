package com.halokonsultan.mobile.ui.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.ui.main.MainActivity

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}