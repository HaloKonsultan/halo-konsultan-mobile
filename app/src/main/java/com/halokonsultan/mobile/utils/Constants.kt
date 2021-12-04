package com.halokonsultan.mobile.utils

import androidx.annotation.StringRes
import com.halokonsultan.mobile.R

// url related stuff
//const val BASE_URL = "http://192.168.1.3:8000/api/users/" // punya Ridlo
//const val BASE_URL = "http://192.168.1.100:8000/api/users/" // punya Galih
const val BASE_URL = "https://api.halokonsultan.me/api/users/" // prod
const val BASE_URL_LOCATION = "https://api.rajaongkir.com/starter/"
const val RAJAONGKIR_API_KEY = "aee020347d9bcebda31efff6ec3eaade"

// shared preference related stuff
const val PREF_KEY = "com.halokonsultan.mobile.PREFERENCE_KEY"
const val PREF_TOKEN = "pref_user_token"
const val PREF_USER_ID = "pref_user_id"
const val PREF_USER_NAME = "pref_user_name"
const val PREF_USER_CITY = "pref_user_city"
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
const val MESSAGE_TYPE_SELF = "client"
const val MESSAGE_TYPE_CONSULTANT = "consultant"

// CONTOH CALLBACK INVOICE
// http://6675-114-79-19-29.ngrok.io/api/users/transaction/invoice_callback

// CONTOH CALLBACK DISBURSEMENT
// http://6675-114-79-19-29.ngrok.io/api/consultants/transaction/withdraw_callback
