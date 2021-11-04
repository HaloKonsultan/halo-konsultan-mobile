package com.halokonsultan.mobile.utils

import androidx.annotation.StringRes
import com.halokonsultan.mobile.R

// url related stuff
const val BASE_URL = "http://192.168.1.100:8000/api/users/"

// shared preference related stuff
const val PREF_KEY = "com.halokonsultan.mobile.PREFERENCE_KEY"
const val PREF_TOKEN = "pref_user_token"
const val PREF_USER_ID = "pref_user_id"
const val PREF_LOGGED_IN = "pref_logged_in"
const val PREF_EXPIRED_TIME = "pref_expired_time"
const val PREF_FIRST_TIME = "pref_first_time"

// tab layout
@StringRes
val TAB_TITLES = intArrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
)

// others
const val SEARCH_USER_TIME_DELAY = 500L