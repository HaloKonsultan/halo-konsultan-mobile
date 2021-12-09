package com.halokonsultan.mobile.ui.consultant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.ConsultantEducation
import com.halokonsultan.mobile.databinding.ItemConsultantEducationBinding

class EducationAdapter: BaseAdapter<EducationAdapter.EducationViewHolder, ConsultantEducation>() {

    inner class EducationViewHolder(val binding: ItemConsultantEducationBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val itemBinding = ItemConsultantEducationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EducationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
        val education = differ.currentList[position]

        with(holder.binding) {
            val yearRange = "${education.start_year} - ${education.end_year}"
            tvInstitutionName.text = education.institution_name
            tvMajor.text = education.major
            tvRangeYear.text = yearRange
        }
    }
}