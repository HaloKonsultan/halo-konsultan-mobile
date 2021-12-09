package com.halokonsultan.mobile.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.BankDocumentFile
import com.halokonsultan.mobile.databinding.ItemBankDocumentBinding

class BankDocumentAdapter: BaseAdapter<BankDocumentAdapter.BankDocumentViewHolder, BankDocumentFile>() {

    inner class BankDocumentViewHolder(val binding: ItemBankDocumentBinding): RecyclerView.ViewHolder(binding.root)

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
            onRvItemClickListener?.let { it(bankDocumentFile) }
        }

        holder.itemView.setOnLongClickListener {
            onLongItemClickListener?.let { it(bankDocumentFile) }
            true
        }
    }

    fun setOnLongItemClickListener(listener: (BankDocumentFile) -> Unit) {
        onLongItemClickListener = listener
    }
}