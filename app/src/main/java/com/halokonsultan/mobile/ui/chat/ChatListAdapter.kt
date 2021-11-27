package com.halokonsultan.mobile.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.data.model.Chat
import com.halokonsultan.mobile.databinding.ItemChatBinding
import com.halokonsultan.mobile.utils.Utils.toBoolean
import com.squareup.picasso.Picasso

class ChatListAdapter: RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    inner class ChatListViewHolder(val binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldChat: Chat, newChat: Chat) =  oldChat == newChat
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((Chat) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemBinding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val chat = differ.currentList[position]
        holder.binding.tvConsultantName.text = chat.consultant_name
        holder.binding.tvChatMessage.text = chat.last_message
        holder.binding.tvChatTime.text = chat.last_message_time
        holder.binding.icon.isVisible = !(chat.last_message_has_read.toBoolean())
        Picasso.get().load(chat.consultant_photo).into(holder.binding.imgPhotoProfile)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(chat) }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (Chat) -> Unit) {
        onItemClickListener = listener
    }
}