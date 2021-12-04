package com.halokonsultan.mobile.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivityConversationBinding
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils.toBoolean
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    private val viewModel: ChatViewModel by viewModels()
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupRecyclerView()
        loadDataFromBundle()
        setupEditTextAndButton()
        setupMessageObserver()
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter()
        with(binding.rvConversation) {
            val llm = LinearLayoutManager(context)
            llm.stackFromEnd = true
            layoutManager = llm
            adapter = messageAdapter
        }
    }

    private fun loadDataFromBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            id = intent.getIntExtra(EXTRA_ID, 0)
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

            getAllMessages()
        }
    }

    private fun getAllMessages() {
        viewModel.getAllMessages(id).observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    viewModel.filterReadMessages(response.data ?: listOf())
                    messageAdapter.differ.submitList(null)
                    messageAdapter.differ.submitList(response.data)
                }
                is Resource.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
            }
        })
    }

    private fun setupEditTextAndButton() {
        if (!binding.llSendMessage.isVisible) return

        binding.etMessage.addTextChangedListener {
            binding.btnSend.isEnabled = it?.isNotBlank() ?: false
        }

        binding.btnSend.setOnClickListener {
            viewModel.sendMessage(id, binding.etMessage.text.toString())
            binding.etMessage.setText("")
        }
    }

    private fun setupMessageObserver() {
        viewModel.messages.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    val temp = messageAdapter.differ.currentList.toMutableList()
                    temp.add(response.data)
                    messageAdapter.differ.submitList(temp)
                }
                is Resource.Error -> {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.miRefresh -> {
                getAllMessages()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.refresh_menu, menu)
        return true
    }
}