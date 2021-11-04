package com.halokonsultan.mobile.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.data.model.BankDocumentFile
import com.halokonsultan.mobile.databinding.ItemBankDocumentBinding
import com.squareup.picasso.Picasso

class BankDocumentAdapter: RecyclerView.Adapter<BankDocumentAdapter.BankDocumentViewHolder>() {

    inner class BankDocumentViewHolder(val binding: ItemBankDocumentBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<BankDocumentFile>() {
        override fun areItemsTheSame(oldItem: BankDocumentFile, newItem: BankDocumentFile)
        = oldItem.file == newItem.file

        override fun areContentsTheSame(oldFile: BankDocumentFile, newFile: BankDocumentFile)
        =  oldFile == newFile
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((BankDocumentFile) -> Unit)? = null
    private var onLongItemClickListener: ((BankDocumentFile) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankDocumentViewHolder {
        val itemBinding = ItemBankDocumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BankDocumentViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BankDocumentViewHolder, position: Int) {
        val bankDocumentFile = differ.currentList[position]

        with(holder.binding) {
            tvDocumentName.text = bankDocumentFile.name
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(bankDocumentFile) }
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClickListener?.let { it(bankDocumentFile) }
            true
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (BankDocumentFile) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnLongItemClickListener(listener: (BankDocumentFile) -> Unit) {
        onLongItemClickListener = listener
    }
}