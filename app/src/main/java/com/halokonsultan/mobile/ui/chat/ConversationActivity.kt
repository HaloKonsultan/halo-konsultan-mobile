package com.halokonsultan.mobile.ui.chat

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.BaseActivity
import com.halokonsultan.mobile.data.model.Message
import com.halokonsultan.mobile.databinding.ActivityConversationBinding
import com.halokonsultan.mobile.utils.PERSON_PLACEHOLDER
import com.halokonsultan.mobile.utils.Utils.addRootDomainIfNeeded
import com.halokonsultan.mobile.utils.Utils.toBoolean
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationActivity : BaseActivity<ActivityConversationBinding>() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CONSULTANT_NAME = "consultant_name"
        const val EXTRA_CONSULTANT_PHOTO = "consultant_photo"
        const val EXTRA_CONSULTANT_CATEGORY = "consultant_category"
        const val EXTRA_IS_ENDED = "is_ended"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityConversationBinding
            = ActivityConversationBinding::inflate

    private lateinit var messageAdapter: MessageAdapter
    private val viewModel: ChatViewModel by viewModels()
    private var id: Int = 0

    override fun setup() {
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
                Picasso.get().load(addRootDomainIfNeeded(photo ?: PERSON_PLACEHOLDER)).into(imgConsultant)
                cardChatEnded.isVisible = isEnded
                llSendMessage.isVisible = !isEnded
            }

            getAllMessages()
        }
    }

    private fun setupAllMessageObserver() = setObserver<List<Message>>(
        onSuccess = { response ->
            viewModel.filterReadMessages(response.data ?: listOf())
            messageAdapter.differ.submitList(null)
            messageAdapter.differ.submitList(response.data)
        },
        onError = { response -> showToast(response.message.toString()) },
        onLoading = { response -> messageAdapter.differ.submitList(response.data) }
    )

    private fun setupMessageObserver() = setObserverWithBasicResponse<Message>(
        onSuccess = { response ->
            val temp = messageAdapter.differ.currentList.toMutableList()
            temp.add(response.data?.data)
            messageAdapter.differ.submitList(temp)
        },
        onError = { response -> showToast(response.message.toString()) }
    )

    private fun getAllMessages() {
        viewModel.getAllMessages(id).observe(this, setupAllMessageObserver())
    }

    private fun setupEditTextAndButton() {
        if (!binding.llSendMessage.isVisible) return

        binding.etMessage.addTextChangedListener {
            binding.btnSend.isEnabled = it?.isNotBlank() ?: false
        }

        binding.btnSend.setOnClickListener {
            viewModel.sendMessage(id, binding.etMessage.text.toString())
                .observe(this, setupMessageObserver())
            binding.etMessage.setText("")
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.refresh_menu, menu)
        return true
    }
}