package com.halokonsultan.mobile.ui.uploaddocument

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.data.model.Document
import com.halokonsultan.mobile.databinding.ItemConsultationDocBinding

class UploadAdapter: RecyclerView.Adapter<UploadAdapter.UploadViewHolder>() {

    inner class UploadViewHolder(val binding: ItemConsultationDocBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Document>() {
        override fun areItemsTheSame(oldItem: Document, newItem: Document) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldDocument: Document, newDocument: Document) =  oldDocument == newDocument
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadViewHolder {
        val itemBinding = ItemConsultationDocBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UploadViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UploadViewHolder, position: Int) {
        val document = differ.currentList[position]

        with(holder.binding) {
            tvDocNumber.text = (position+1).toString()
            tvDocTitle.text = document.docTitle
            tvDocDesc.text = document.docDesc
            if (document.file.isEmpty()) {
                btnUnggah.text = "Unggah"
            } else {
                btnUnggah.text = "Edit"
                imgCheck.isVisible = true
            }

        }
    }

    override fun getItemCount() = differ.currentList.size
}