package com.halokonsultan.mobile.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.databinding.FragmentChatBinding
import com.halokonsultan.mobile.utils.GlobalState
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatListAdapter: ChatListAdapter
    private val viewModel: ChatViewModel by viewModels()
    private var loading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwiper()

        getChatList(1, false)
    }

    private fun getChatList(page: Int, shouldAppend: Boolean) {
        viewModel.getChatList(page).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    if (shouldAppend) {
                        val temp = chatListAdapter.differ.currentList.toMutableList()
                        response.data?.let { temp.addAll(it) }
                        chatListAdapter.differ.submitList(temp)
                        loading = false
                    } else {
                        chatListAdapter.differ.submitList(response.data)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                    chatListAdapter.differ.submitList(response.data)
                }
            }
        })
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