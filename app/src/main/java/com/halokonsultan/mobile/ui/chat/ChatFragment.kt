package com.halokonsultan.mobile.ui.chat

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseFragment
import com.halokonsultan.mobile.data.model.Chat
import com.halokonsultan.mobile.databinding.FragmentChatBinding
import com.halokonsultan.mobile.utils.GlobalState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>() {

    private lateinit var chatListAdapter: ChatListAdapter
    private val viewModel: ChatViewModel by viewModels()
    private var loading: Boolean = false
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChatBinding
            = FragmentChatBinding::inflate

    override fun setup() {
        setupRecyclerView()
        setupSwiper()
        getChatList(1, false)
        binding.btnRefresh.setOnClickListener {
            getChatList(1, false)
        }
    }

    private fun setupChatObserver(shouldAppend: Boolean) = setObserver<List<Chat>>(
        onSuccess = { response ->
            binding.progressBar.gone()
            binding.layNoInet.gone()

            if (response.data!!.isNotEmpty()) {
                if (shouldAppend) {
                    val temp = chatListAdapter.differ.currentList.toMutableList()
                    response.data.let { temp.addAll(it) }
                    chatListAdapter.differ.submitList(temp)
                    loading = false
                } else {
                    chatListAdapter.differ.submitList(response.data)
                }
            } else {
                binding.layNoResult.visible()
            }
        },
        onError = { response ->
            binding.progressBar.gone()
            binding.layNoResult.gone()
            binding.layNoInet.visible()
            showToast(response.message.toString())
        },
        onLoading = { response ->
            binding.progressBar.visible()
            chatListAdapter.differ.submitList(response.data)
        }
    )

    private fun getChatList(page: Int, shouldAppend: Boolean) {
        viewModel.getChatList(page).observe(viewLifecycleOwner, setupChatObserver(shouldAppend))
    }

    private fun setupRecyclerView() {
        chatListAdapter = ChatListAdapter()
        with(binding.rvChatList) {
            layoutManager = LinearLayoutManager(context)
            adapter = chatListAdapter

            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                    val manager = this@with.layoutManager as LinearLayoutManager
                    val max = rv.adapter?.itemCount?.minus(1)
                    if (
                        GlobalState.nextPageChat != null
                        && manager.findLastCompletelyVisibleItemPosition() == max
                        && !loading
                    ) {
                        loading = true
                        getChatList(GlobalState.nextPageChat!!, true)
                    }
                }
            })
        }

        chatListAdapter.setOnItemClickListener {
            val intent = Intent(binding.root.context, ConversationActivity::class.java)
            intent.putExtra(ConversationActivity.EXTRA_ID, it.id)
            intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_ID, it.consultant_id)
            intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_NAME, it.name)
            intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_PHOTO, it.photo)
            intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_CATEGORY, it.category)
            intent.putExtra(ConversationActivity.EXTRA_IS_ENDED, it.is_ended)
            startActivity(intent)
        }
    }

    private fun setupSwiper() {
        binding.swiper.setOnRefreshListener {
            getChatList(1, false)
        }
    }
}