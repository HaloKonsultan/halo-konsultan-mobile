package com.halokonsultan.mobile.ui.consultant

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.halokonsultan.mobile.data.model.ConsultantDoc
import com.halokonsultan.mobile.databinding.ItemConsultantDocumentationBinding

class DocumentationAdapter: RecyclerView.Adapter<DocumentationAdapter.DocumentationViewHolder>() {

    inner class DocumentationViewHolder(val binding: ItemConsultantDocumentationBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ConsultantDoc>() {
        override fun areItemsTheSame(oldItem: ConsultantDoc, newItem: ConsultantDoc) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldConsultantDoc: ConsultantDoc, newConsultantDoc: ConsultantDoc) =
                                        oldConsultantDoc == newConsultantDoc
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentationViewHolder {
        val itemBinding = ItemConsultantDocumentationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DocumentationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DocumentationViewHolder, position: Int) {
        val documentation = differ.currentList[position]
        Glide.with(holder.itemView.context)
                .load(Uri.parse(documentation.photo))
                .apply(RequestOptions().override(210, 160))
                .into(holder.binding.imgConsultantDocumentation)
    }

    override fun getItemCount() = differ.currentList.size
}