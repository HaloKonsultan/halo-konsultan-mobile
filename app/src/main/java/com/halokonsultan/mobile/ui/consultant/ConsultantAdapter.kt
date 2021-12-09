package com.halokonsultan.mobile.ui.consultant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.databinding.ItemConsultantBinding
import com.halokonsultan.mobile.utils.PERSON_PLACEHOLDER
import com.halokonsultan.mobile.utils.Utils.addRootDomainIfNeeded
import com.squareup.picasso.Picasso

class ConsultantAdapter: BaseAdapter<ConsultantAdapter.ConsultantViewHolder, Consultant>() {

    inner class ConsultantViewHolder(val binding: ItemConsultantBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultantViewHolder {
        val itemBinding = ItemConsultantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConsultantViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ConsultantViewHolder, position: Int) {
        val consultant = differ.currentList[position]

        with(holder.binding) {
            val loveStat = "${consultant.likes_total} orang menyukai konsultan ini."
            tvConsultantName.text = consultant.name
            tvConsultantCategory.text = consultant.position
            tvConsultantLocation.text = consultant.city
            tvConsultantLovesStat.text = loveStat

            Picasso.get().load(addRootDomainIfNeeded(consultant.photo ?: PERSON_PLACEHOLDER))
                .resize(56, 56)
                .centerCrop()
                .into(imgPhotoProfile)
        }

        holder.itemView.setOnClickListener {
            onRvItemClickListener?.let { it(consultant) }
        }
    }
}