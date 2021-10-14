package com.halokonsultan.mobile.ui.consultant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.data.model.ConsultantExperience
import com.halokonsultan.mobile.databinding.ItemConsultantExperienceBinding

class ExperienceAdapter: RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>() {

    inner class ExperienceViewHolder(val binding: ItemConsultantExperienceBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ConsultantExperience>() {
        override fun areItemsTheSame(oldItem: ConsultantExperience, newItem: ConsultantExperience) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldConsultantExperience: ConsultantExperience,
                                        newConsultantExperience: ConsultantExperience)
                                        = oldConsultantExperience == newConsultantExperience
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val itemBinding = ItemConsultantExperienceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExperienceViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val experience = differ.currentList[position]

        with(holder.binding) {
            val yearRange = "${experience.start_year} - ${experience.end_year}"
            tvExperienceName.text = experience.poistion
            tvRangeYear.text = yearRange
        }
    }

    override fun getItemCount() = differ.currentList.size
}