package com.halokonsultan.mobile.ui.consultation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.databinding.ItemConsultationBinding
import com.halokonsultan.mobile.utils.TAB_TITLES
import com.halokonsultan.mobile.utils.Utils.toString
import java.util.*

class ConsultationAdapter(
    private val type: Int
): RecyclerView.Adapter<ConsultationAdapter.ConsultationViewHolder>() {

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
            when (type) {
                TAB_TITLES[0] -> {
                    tvConsultationTime.text = Date(consultation.date).toString("dd/MM/yyyy hh:mm")
                    tvConsultationTime.visibility = View.VISIBLE
                }
                TAB_TITLES[1] -> {
                    if (consultation.is_confirmed) {
                        tvConsultationStatus.text = "Menunggu Pembayaran"
                        tvConsultationStatus.setTextColor(this.root.resources.getColor(R.color.green))
                    } else {
                        tvConsultationStatus.text = "Menunggu Konfirmasi Konsultan"
                        tvConsultationStatus.setTextColor(this.root.resources.getColor(R.color.orange))
                    }
                    tvConsultationStatus.visibility = View.VISIBLE
                }
                TAB_TITLES[2] -> {
                    if (consultation.is_confirmed) {
                        tvConsultationStatus.text = "selesai"
                        tvConsultationStatus.setTextColor(this.root.resources.getColor(R.color.primary_blue))
                    } else {
                        tvConsultationStatus.text = "ditolak"
                        tvConsultationStatus.setTextColor(this.root.resources.getColor(R.color.danger))
                    }
                    tvConsultationStatus.visibility = View.VISIBLE
                }
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