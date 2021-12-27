package com.halokonsultan.mobile.ui.home

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.databinding.FragmentHomeBinding
import com.halokonsultan.mobile.databinding.ItemConsultationBinding
import com.halokonsultan.mobile.utils.Utils
import com.halokonsultan.mobile.utils.Utils.toString

class LatestConsultationAdapter():
    BaseAdapter<LatestConsultationAdapter.LatestConsultationViewHolder, Consultation>() {
    inner class LatestConsultationViewHolder(val binding: ItemConsultationBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LatestConsultationAdapter.LatestConsultationViewHolder {
        val itemBinding = ItemConsultationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LatestConsultationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: LatestConsultationAdapter.LatestConsultationViewHolder,
        position: Int
    ) {
        val latestConsultation = differ.currentList[position]

        with(holder.binding) {
            tvConsultationTitle.text = latestConsultation.title
            tvConsultantName.text = latestConsultation.name

            if (latestConsultation.status == "waiting") {
                if (latestConsultation.is_confirmed == 1) {
                    tvConsultationStatus.text = root.context.getString(R.string.menunggu_pembayaran)
                    tvConsultationStatus.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.green
                        )
                    )
                } else {
                    tvConsultationStatus.text = root.context.getString(R.string.menunggu_konfirmasi_konsultan)
                    tvConsultationStatus.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.orange
                        )
                    )
                }

                tvConsultationStatus.visibility = View.VISIBLE
            } else if (latestConsultation.status == "active") {
                val formattedDate = Utils.strDate(latestConsultation.date ?: "01-01-2000")
                ?.toString("EEE, dd MMM yyyy")
                tvConsultationTime.text = root.context
                    .getString(R.string.formatter_tanggal_jam, formattedDate, latestConsultation.time)
                tvConsultationTime.visibility = View.VISIBLE
            }
        }

        holder.itemView.setOnClickListener {
            onRvItemClickListener?.let { it(latestConsultation) }
        }
    }
}

