package com.halokonsultan.mobile.ui.confirmation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.halokonsultan.mobile.databinding.ActivityConfirmationBinding
import com.halokonsultan.mobile.databinding.ActivityConsultantBinding
import com.halokonsultan.mobile.ui.main.MainActivity

class ConfirmationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_MESSAGE = "extra_message"
    }

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            val title = intent.getStringExtra(EXTRA_TITLE)
            val message = intent.getStringExtra(EXTRA_MESSAGE)

            binding.tvTitle.text = title
            binding.tvMessage.text = message

            binding.btnBackHome.setOnClickListener {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}