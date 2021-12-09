package com.halokonsultan.mobile.ui.consultant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.ConsultantDoc
import com.halokonsultan.mobile.databinding.ItemConsultantDocumentationBinding
import com.halokonsultan.mobile.utils.Utils.addRootDomainIfNeeded
import com.squareup.picasso.Picasso

class DocumentationAdapter: BaseAdapter<DocumentationAdapter.DocumentationViewHolder, ConsultantDoc>() {

    inner class DocumentationViewHolder(val binding: ItemConsultantDocumentationBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentationViewHolder {
        val itemBinding = ItemConsultantDocumentationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DocumentationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DocumentationViewHolder, position: Int) {
        val documentation = differ.currentList[position]
        Picasso.get().load(addRootDomainIfNeeded(documentation.photo))
            .resize(210,160)
            .centerCrop()
            .into(holder.binding.imgConsultantDocumentation)
    }
}