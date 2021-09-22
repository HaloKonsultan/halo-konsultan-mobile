package com.halokonsultan.mobile.ui.consultation

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.databinding.ItemCategoryBinding
import com.halokonsultan.mobile.databinding.ItemConsultationBinding

class ConsultationAdapter: RecyclerView.Adapter<ConsultationAdapter.ConsultationViewHolder>() {

    inner class ConsultationViewHolder(val binding: ItemConsultationBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Consultation>() {
        override fun areItemsTheSame(oldItem: Consultation, newItem: Consultation) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldConsultation: Consultation, newConsultation: Consultation) =
            oldConsultation == newConsultation
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((Consultation) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultationViewHolder {
        val itemBinding = ItemConsultationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConsultationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ConsultationViewHolder, position: Int) {
        val consultation = differ.currentList[position]

        with(holder.binding) {
            tvConsultationTitle.text = consultation.title
            tvConsultantName.text = consultation.consultant_name
            if (consultation.status.equals("aktif")) {
                tvConsultationTime.text = consultation.date.toString()
                tvConsultationTime.visibility = View.VISIBLE
            } else {
                tvConsultationStatus.text = consultation.status
                tvConsultationStatus.visibility = View.VISIBLE
            }
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(consultation) }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (Consultation) -> Unit) {
        onItemClickListener = listener
    }
}