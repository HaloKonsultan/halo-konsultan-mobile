package com.halokonsultan.mobile.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.FragmentChatBinding
import com.halokonsultan.mobile.ui.consultant.ConsultantActivity
import com.halokonsultan.mobile.ui.consultant.ConsultantAdapter
import com.halokonsultan.mobile.utils.DataDummy
import com.halokonsultan.mobile.utils.GlobalState
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

        chatListAdapter.differ.submitList(DataDummy.getChatList())
    }

    private fun setupRecyclerView() {
        chatListAdapter = ChatListAdapter()
        with(binding.rvChatList) {
            layoutManager = LinearLayoutManager(context)
            adapter = chatListAdapter

//            addOnScrollListener(object: RecyclerView.OnScrollListener() {
//                override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
//                    val manager = this@with.layoutManager as LinearLayoutManager
//                    val max = rv.adapter?.itemCount?.minus(1)
//                    if (
//                        com.halokonsultan.mobile.utils.GlobalState.nextPageConsultant != null
//                        && manager.findLastCompletelyVisibleItemPosition() == max
//                        && !loading
//                    ) {
//                        loading = true
//                        getNearestConsultantData(com.halokonsultan.mobile.utils.GlobalState.nextPageConsultant!!, true)
//                    }
//                }
//            })
        }

        chatListAdapter.setOnItemClickListener {
            val intent = Intent(binding.root.context, ConversationActivity::class.java)
            intent.putExtra(ConversationActivity.EXTRA_ID, it.id)
            intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_NAME, it.consultant_name)
            intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_PHOTO, it.consultant_photo)
            intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_CATEGORY, it.consultant_category)
            intent.putExtra(ConversationActivity.EXTRA_IS_ENDED, it.is_ended)
            startActivity(intent)
        }
    }
}