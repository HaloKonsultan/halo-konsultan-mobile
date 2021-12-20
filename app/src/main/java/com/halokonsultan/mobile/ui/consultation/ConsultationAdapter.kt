package com.halokonsultan.mobile.ui.consultation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.databinding.ItemConsultationBinding
import com.halokonsultan.mobile.utils.TAB_TITLES
import com.halokonsultan.mobile.utils.Utils
import com.halokonsultan.mobile.utils.Utils.toString

class ConsultationAdapter(
    private val type: Int
): BaseAdapter<ConsultationAdapter.ConsultationViewHolder, Consultation>() {

    inner class ConsultationViewHolder(val binding: ItemConsultationBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultationViewHolder {
        val itemBinding = ItemConsultationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConsultationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ConsultationViewHolder, position: Int) {
        val consultation = differ.currentList[position]

        with(holder.binding) {
            tvConsultationTitle.text = consultation.title
            tvConsultantName.text = consultation.name
            when (type) {
                TAB_TITLES[0] -> {
                    val formattedDate = Utils.strDate(consultation.date ?: "01-01-2000")
                        ?.toString("EEE, dd MMM yyyy")
                    tvConsultationTime.text = root.context
                        .getString(R.string.formatter_tanggal_jam, formattedDate, consultation.time)
                    tvConsultationTime.visibility = View.VISIBLE
                }

                TAB_TITLES[1] -> {
                    if (consultation.is_confirmed == 1) {
                        tvConsultationStatus.text = root.context.getString(R.string.menunggu_pembayaran)
                        tvConsultationStatus.setTextColor(getColor(root.context, R.color.green))
                    } else {
                        tvConsultationStatus.text = root.context.getString(R.string.menunggu_konfirmasi_konsultan)
                        tvConsultationStatus.setTextColor(getColor(root.context, R.color.orange))
                    }

                    tvConsultationStatus.visibility = View.VISIBLE
                }

                TAB_TITLES[2] -> {
                    if (consultation.is_confirmed == 1) {
                        tvConsultationStatus.text = root.context.getString(R.string.selesai)
                        tvConsultationStatus.setTextColor(getColor(root.context, R.color.primary_blue))
                    } else {
                        tvConsultationStatus.text = root.context.getString(R.string.ditolak)
                        tvConsultationStatus.setTextColor(getColor(root.context, R.color.danger))
                    }

                    tvConsultationStatus.visibility = View.VISIBLE
                }
            }
        }

        holder.itemView.setOnClickListener {
            onRvItemClickListener?.let { it(consultation) }
        }
    }
}