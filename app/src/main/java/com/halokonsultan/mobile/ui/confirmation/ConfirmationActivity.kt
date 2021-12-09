package com.halokonsultan.mobile.ui.confirmation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.halokonsultan.mobile.base.BaseActivity
import com.halokonsultan.mobile.databinding.ActivityConfirmationBinding
import com.halokonsultan.mobile.databinding.ActivityConsultantBinding
import com.halokonsultan.mobile.ui.main.MainActivity

class ConfirmationActivity : BaseActivity<ActivityConfirmationBinding>() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_MESSAGE = "extra_message"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityConfirmationBinding
        = ActivityConfirmationBinding::inflate

    override fun setup() {
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