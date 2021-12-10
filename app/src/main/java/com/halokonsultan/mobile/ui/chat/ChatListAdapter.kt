package com.halokonsultan.mobile.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.Chat
import com.halokonsultan.mobile.databinding.ItemChatBinding
import com.halokonsultan.mobile.utils.MESSAGE_TYPE_CONSULTANT
import com.halokonsultan.mobile.utils.Utils.addRootDomainIfNeeded
import com.halokonsultan.mobile.utils.Utils.toBoolean
import com.halokonsultan.mobile.utils.Utils.trim
import com.squareup.picasso.Picasso

class ChatListAdapter: BaseAdapter<ChatListAdapter.ChatListViewHolder, Chat>() {

    inner class ChatListViewHolder(val binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemBinding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val chat = differ.currentList[position]
        holder.binding.apply {
            tvConsultantName.text = chat.name
            tvChatMessage.text = (chat.last_message?: "").trim(40)
            tvChatTime.text = chat.last_messages_time
            icon.isVisible = (chat.last_messages_from == MESSAGE_TYPE_CONSULTANT)
                && !(chat.last_messages_is_read!!.toBoolean())
            Picasso.get().load(addRootDomainIfNeeded(chat.photo)).into(imgPhotoProfile)
        }

        holder.itemView.setOnClickListener {
            onRvItemClickListener?.let { it(chat) }
        }
    }
}