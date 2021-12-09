package com.halokonsultan.mobile.ui.consultant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.base.BaseAdapter
import com.halokonsultan.mobile.data.model.ConsultantSkill
import com.halokonsultan.mobile.databinding.ItemConsultantSkillBinding

class SkillAdapter: BaseAdapter<SkillAdapter.SkillViewHolder, ConsultantSkill>() {

    inner class SkillViewHolder(val binding: ItemConsultantSkillBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val itemBinding = ItemConsultantSkillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SkillViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val skill = differ.currentList[position]
        holder.binding.tvSkillName.text = skill.skills
    }
}