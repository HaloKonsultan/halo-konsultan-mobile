package com.halokonsultan.mobile.ui.uploaddocument

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.ConsultationsDocument
import com.halokonsultan.mobile.databinding.ItemConsultationDocBinding

class UploadAdapter: BaseAdapter<UploadAdapter.UploadViewHolder, ConsultationsDocument>() {

    inner class UploadViewHolder(val binding: ItemConsultationDocBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadViewHolder {
        val itemBinding = ItemConsultationDocBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UploadViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UploadViewHolder, position: Int) {
        val document = differ.currentList[position]

        with(holder.binding) {
            tvDocNumber.text = (position+1).toString()
            tvDocTitle.text = document.name
            tvDocDesc.text = document.description
            if (document.file == null) btnUnggah.text = "Unggah"
            else {
                btnUnggah.text = "Edit"
                imgCheck.isVisible = true
            }
            btnUnggah.setOnClickListener {
                onRvItemClickListener?.let { func -> func(document) }
            }
        }
    }
}