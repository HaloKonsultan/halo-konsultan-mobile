package com.halokonsultan.mobile.ui.onboarding

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.halokonsultan.mobile.databinding.ViewOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint
import setParallaxTransformation

@AndroidEntryPoint
class OnBoardingView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val numberOfPages by lazy { OnBoardingPage.values().size }
    private val binding: ViewOnboardingBinding =
        ViewOnboardingBinding.inflate(LayoutInflater.from(context), this, true)
    private var listener: MutableList<OnBoardingChangeListener> = mutableListOf()

    init {
        setUpSlider()
        addingButtonsClickListeners()
    }

    fun addListener(item: OnBoardingChangeListener) {
        listener.add(item)
    }

    private fun setUpSlider() {
        with(binding.slider) {
            adapter = OnBoardingAdapter()
            setPageTransformer { page, position ->
                setParallaxTransformation(page, position)
            }

            addSlideChangeListener()

            val wormDotsIndicator = binding.pageIndicator
            wormDotsIndicator.setViewPager2(this)
        }
    }

    private fun addSlideChangeListener() {
        binding.slider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (numberOfPages > 1) {
                    val newProgress = (position + positionOffset) / (numberOfPages - 1)
                    binding.onboardingRoot.progress = newProgress
                }
            }
        })
    }

    private fun addingButtonsClickListeners() {
        with(binding) {
            nextBtn.setOnClickListener { navigateToNextSlide() }
            skipBtn.setOnClickListener {
                listener.forEach {
                    it.onFinish()
                }
            }
            startBtn.setOnClickListener {
                listener.forEach {
                    it.onFinish()
                }
            }
        }
    }

    private fun navigateToNextSlide() {
        val nextSlidePos: Int = binding.slider.currentItem.plus(1)
        binding.slider.setCurrentItem(nextSlidePos, true)
    }

    interface OnBoardingChangeListener {
        fun onFinish()
    }
}