package com.halokonsultan.mobile.ui.consultant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.ConsultantExperience
import com.halokonsultan.mobile.databinding.ItemConsultantExperienceBinding

class ExperienceAdapter: BaseAdapter<ExperienceAdapter.ExperienceViewHolder, ConsultantExperience>() {

    inner class ExperienceViewHolder(val binding: ItemConsultantExperienceBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val itemBinding = ItemConsultantExperienceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExperienceViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val experience = differ.currentList[position]

        with(holder.binding) {
            val yearRange = "${experience.start_year} - ${experience.end_year}"
            tvExperienceName.text = experience.position
            tvRangeYear.text = yearRange
        }
    }
}