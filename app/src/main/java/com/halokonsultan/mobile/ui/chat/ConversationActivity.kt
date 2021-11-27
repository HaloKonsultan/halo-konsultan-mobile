package com.halokonsultan.mobile.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.databinding.ActivityConversationBinding
import com.halokonsultan.mobile.utils.DataDummy
import com.halokonsultan.mobile.utils.Utils.toBoolean
import com.squareup.picasso.Picasso

class ConversationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CONSULTANT_NAME = "consultant_name"
        const val EXTRA_CONSULTANT_PHOTO = "consultant_photo"
        const val EXTRA_CONSULTANT_CATEGORY = "consultant_category"
        const val EXTRA_IS_ENDED = "is_ended"
    }

    private lateinit var binding: ActivityConversationBinding
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupRecyclerView()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        loadDataFromBundle()
        setupEditTextAndButton()

        messageAdapter.differ.submitList(DataDummy.getMessages())
    }

    private fun setupEditTextAndButton() {
        if (!binding.llSendMessage.isVisible) return

        binding.etMessage.addTextChangedListener {
            binding.btnSend.isEnabled = it?.isNotBlank() ?: false
        }

        binding.btnSend.setOnClickListener {
            Toast.makeText(this, binding.etMessage.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadDataFromBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            val id = intent.getIntExtra(EXTRA_ID, 0)
            val name = intent.getStringExtra(EXTRA_CONSULTANT_NAME)
            val photo = intent.getStringExtra(EXTRA_CONSULTANT_PHOTO)
            val category = intent.getStringExtra(EXTRA_CONSULTANT_CATEGORY)
            val isEnded = intent.getIntExtra(EXTRA_IS_ENDED, 0).toBoolean()

            with(binding) {
                tvConsultantName.text = name
                tvConsultantCategory.text = category
                Picasso.get().load(photo).into(imgConsultant)
                cardChatEnded.isVisible = isEnded
                llSendMessage.isVisible = !isEnded
            }
        }
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter()
        with(binding.rvConversation) {
            layoutManager = LinearLayoutManager(context)
            adapter = messageAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}