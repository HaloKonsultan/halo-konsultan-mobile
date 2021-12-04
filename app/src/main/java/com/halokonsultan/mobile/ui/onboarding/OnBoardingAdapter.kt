package com.halokonsultan.mobile.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.halokonsultan.mobile.R

class OnBoardingAdapter(private val onBoardingPageList:Array<OnBoardingPage> = OnBoardingPage.values())
    : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PagerViewHolder {
        return LayoutInflater.from(parent.context).inflate(
            PagerViewHolder.LAYOUT, parent, false
        ).let { PagerViewHolder(it) }
    }

    override fun getItemCount() = onBoardingPageList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(onBoardingPageList[position])
    }
}

class PagerViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
    fun bind(onBoardingPage: OnBoardingPage) {
        val res = root.context.resources
        root.findViewById<TextView>(R.id.tv_title).text = res.getString(onBoardingPage.titleResource)
        root.findViewById<TextView>(R.id.tv_desc).text = res.getString(onBoardingPage.descriptionResource)
        root.findViewById<LottieAnimationView>(R.id.img_animation).setAnimation(onBoardingPage.iconResource)
    }

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.item_page_onboarding
    }
}