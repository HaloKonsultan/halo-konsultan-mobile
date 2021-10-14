package com.halokonsultan.mobile.utils

import androidx.annotation.StringRes
import com.halokonsultan.mobile.R

// url related stuff
const val BASE_URL = "http://192.168.1.100:8000/api/user/"
const val TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODAwMFwvYXBpXC91c2VyXC9sb2dpbiIsImlhdCI6MTYzMzc2NjUwMSwiZXhwIjoxNjMzNzcwMTAxLCJuYmYiOjE2MzM3NjY1MDEsImp0aSI6ImRRWUNmZ0xpMkVSWHc0ejEiLCJzdWIiOjcsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.r4jUvev3PsYjEhAM2_KU93K8a9vv7flnu4IAmimET_Y"

// shared preference related stuff
const val PREF_KEY = "com.halokonsultan.mobile.PREFERENCE_KEY"
const val PREF_TOKEN = "pref_user_token"
const val PREF_USER_ID = "pref_user_id"
const val PREF_LOGGED_IN = "pref_logged_in"

// tab layout
@StringRes
val TAB_TITLES = intArrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
)

// others
const val SEARCH_USER_TIME_DELAY = 500L