package com.halokonsultan.mobile.utils

import androidx.annotation.StringRes
import com.halokonsultan.mobile.R

// url related stuff
//const val BASE_URL = "http://192.168.1.3:8000/api/users/" // punya Ridlo
//const val BASE_URL = "http://192.168.1.101:8000/api/users/" // punya Galih
const val BASE_URL = "http://192.168.130.98:8000/api/users/" // punya Galih 2
//const val BASE_URL = "https://api.halokonsultan.me/api/users/" // prod
const val BASE_URL_LOCATION = "https://api.binderbyte.com/wilayah/"
const val BINDERBYTE_API_KEY = "079fc527c1d3fdf63c64cc384bc51b9e6fff9b7552c8eb493db7b2035d70c421"

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
