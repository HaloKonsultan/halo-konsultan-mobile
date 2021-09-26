package com.halokonsultan.mobile.ui.consultant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.data.model.ConsultantSkill
import com.halokonsultan.mobile.databinding.ItemConsultantSkillBinding

class SkillAdapter: RecyclerView.Adapter<SkillAdapter.SkillViewHolder>() {

    inner class SkillViewHolder(val binding: ItemConsultantSkillBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ConsultantSkill>() {
        override fun areItemsTheSame(oldItem: ConsultantSkill, newItem: ConsultantSkill) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldConsultantSkill: ConsultantSkill,
                                        newConsultantSkill: ConsultantSkill)
                = oldConsultantSkill == newConsultantSkill
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val itemBinding = ItemConsultantSkillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SkillViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val skill = differ.currentList[position]
        holder.binding.tvSkillName.text = skill.skills
    }

    override fun getItemCount() = differ.currentList.size
}