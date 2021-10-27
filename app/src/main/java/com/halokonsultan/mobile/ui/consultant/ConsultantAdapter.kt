package com.halokonsultan.mobile.ui.consultant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.databinding.ItemConsultantBinding
import com.squareup.picasso.Picasso

class ConsultantAdapter: RecyclerView.Adapter<ConsultantAdapter.ConsultantViewHolder>() {

    inner class ConsultantViewHolder(val binding: ItemConsultantBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Consultant>() {
        override fun areItemsTheSame(oldItem: Consultant, newItem: Consultant) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldConsultant: Consultant, newConsultant: Consultant) =  oldConsultant == newConsultant
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((Consultant) -> Unit)? = null

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
            tvConsultantLocation.text = consultant.location
            tvConsultantLovesStat.text = loveStat

            Picasso.get().load(consultant.photo)
                .resize(56, 56)
                .centerCrop()
                .into(imgPhotoProfile)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(consultant) }
        }
    }

    override fun getItemCount() = differ.currentList.size

    fun setOnItemClickListener(listener: (Consultant) -> Unit) {
        onItemClickListener = listener
    }
}