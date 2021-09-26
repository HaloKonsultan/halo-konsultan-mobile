package com.halokonsultan.mobile.ui.consultant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.data.model.ConsultantEducation
import com.halokonsultan.mobile.databinding.ItemConsultantEducationBinding

class EducationAdapter: RecyclerView.Adapter<EducationAdapter.EducationViewHolder>() {

    inner class EducationViewHolder(val binding: ItemConsultantEducationBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ConsultantEducation>() {
        override fun areItemsTheSame(oldItem: ConsultantEducation, newItem: ConsultantEducation) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldConsultantEducation: ConsultantEducation,
                                        newConsultantEducation: ConsultantEducation)
                = oldConsultantEducation == newConsultantEducation
    }

    val differ = AsyncListDiffer(this, differCallback)

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

    override fun getItemCount() = differ.currentList.size
}