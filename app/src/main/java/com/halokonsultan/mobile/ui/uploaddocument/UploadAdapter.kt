package com.halokonsultan.mobile.ui.uploaddocument

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.data.model.ConsultationsDocument
import com.halokonsultan.mobile.databinding.ItemConsultationDocBinding

class UploadAdapter: RecyclerView.Adapter<UploadAdapter.UploadViewHolder>() {

    inner class UploadViewHolder(val binding: ItemConsultationDocBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ConsultationsDocument>() {
        override fun areItemsTheSame(oldItem: ConsultationsDocument, newItem: ConsultationsDocument) = oldItem.name == newItem.name

        override fun areContentsTheSame(oldDocument: ConsultationsDocument, newDocument: ConsultationsDocument) =  oldDocument == newDocument
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onUploadClickListener: ((ConsultationsDocument) -> Unit)? = null

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
                onUploadClickListener?.let { func -> func(document) }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnUploadClickListener(listener: (ConsultationsDocument) -> Unit) {
        onUploadClickListener = listener
    }
}