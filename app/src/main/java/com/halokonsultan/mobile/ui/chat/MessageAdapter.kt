package com.halokonsultan.mobile.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.data.model.Message
import com.halokonsultan.mobile.databinding.ItemChatConsultantBinding
import com.halokonsultan.mobile.databinding.ItemChatSelfBinding
import com.halokonsultan.mobile.utils.MESSAGE_TYPE_SELF
import com.halokonsultan.mobile.utils.Utils.toBoolean
import com.halokonsultan.mobile.utils.Utils.toInt


class MessageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class MessageSelfViewHolder(val binding: ItemChatSelfBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val message = differ.currentList[position]
            binding.tvMessage.text = message.message
            binding.messageTime.text = message.time
            binding.imgHasRead.isVisible = message.is_read.toBoolean()
        }
    }

    private inner class MessageConsultantViewHolder(val binding: ItemChatConsultantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val message = differ.currentList[position]
            binding.tvMessage.text = message.message
            binding.messageTime.text = message.time
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldMessage: Message, newMessage: Message) =  oldMessage == newMessage
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val itemView = ItemChatSelfBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MessageSelfViewHolder(itemView)
        } else {
            val itemView = ItemChatConsultantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MessageConsultantViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (differ.currentList[position].sender === MESSAGE_TYPE_SELF) {
            (holder as MessageSelfViewHolder).bind(position)
        } else {
            (holder as MessageConsultantViewHolder).bind(position)
        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return (differ.currentList[position].sender == MESSAGE_TYPE_SELF).toInt()
    }
}