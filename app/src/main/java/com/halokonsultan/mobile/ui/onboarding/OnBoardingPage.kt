package com.halokonsultan.mobile.ui.onboarding

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.halokonsultan.mobile.R

enum class OnBoardingPage(
    @StringRes val titleResource: Int,
    @StringRes val descriptionResource: Int,
    @RawRes val iconResource: Int
) {

    ONE(R.string.cari_konsultan, R.string.desc_cari_konsultan, R.raw.cari_icon_revtwo),
    TWO(R.string.chat_konsultan, R.string.desc_chat_konsultan, R.raw.chat_icon),
    THREE(R.string.booking_konsultasi, R.string.desc_booking_konsultasi, R.raw.booking_icon_revtwo)

}