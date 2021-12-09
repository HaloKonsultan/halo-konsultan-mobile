package com.halokonsultan.mobile.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.halokonsultan.mobile.databinding.ItemPageOnboardingBinding

class OnBoardingAdapter(private val onBoardingPageList:Array<OnBoardingPage> = OnBoardingPage.values())
    : RecyclerView.Adapter<OnBoardingAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(val binding: ItemPageOnboardingBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PagerViewHolder {
        val itemBinding = ItemPageOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(itemBinding)
    }

    override fun getItemCount() = onBoardingPageList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val page = onBoardingPageList[position]
        val res = holder.binding.root.context
        with(holder.binding) {
            tvTitle.text = res.getString(page.titleResource)
            tvDesc.text = res.getString(page.descriptionResource)
            imgAnimation.setAnimation(page.iconResource)
        }
    }
}