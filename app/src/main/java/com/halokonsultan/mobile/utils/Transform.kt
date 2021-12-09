package com.halokonsultan.mobile.utils

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.halokonsultan.mobile.R

fun setParallaxTransformation(page: View, position: Float){
    page.apply {
        val parallaxView = this.findViewById<LottieAnimationView>(R.id.img_animation)
        when {
            position < -1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                alpha = 1f
            position <= 1 -> { // [-1,1]
                parallaxView.translationX = -position * (width / 2) //Half the normal speed
            }
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                alpha = 1f
        }
    }

}