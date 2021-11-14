package com.halokonsultan.mobile.data.preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.halokonsultan.mobile.HaloKonsultanApplication
import com.halokonsultan.mobile.utils.*
import java.util.*


class Preferences private constructor() {
    private val mPrefs: SharedPreferences
    private val mEdit: SharedPreferences.Editor

    val token: String?
        get() = instance.mPrefs.getString(PREF_TOKEN, "")

    val userID: Int
        get() = instance.mPrefs.getInt(PREF_USER_ID, 0)

    val userCity: String?
        get() = instance.mPrefs.getString(PREF_USER_CITY, "")

    val loggedIn: Boolean
        get() = instance.mPrefs.getBoolean(PREF_LOGGED_IN, false)

    val expiredTime: Long
        get() = instance.mPrefs.getLong(PREF_EXPIRED_TIME, 0L)

    val firstTime: Boolean
        get() = instance.mPrefs.getBoolean(PREF_FIRST_TIME, true)

    fun saveToken(value: String?) {
        mEdit.putString(PREF_TOKEN, value)
        mEdit.apply()
    }

    fun saveUserId(value: Int) {
        mEdit.putInt(PREF_USER_ID, value)
        mEdit.apply()
    }

    fun saveUserCity(value: String?) {
        mEdit.putString(PREF_USER_CITY, value)
        mEdit.apply()
    }

    fun isLoggedIn(value: Boolean) {
        mEdit.putBoolean(PREF_LOGGED_IN, value)
        mEdit.apply()
    }

    fun setExpirationTime(value: Int) {
        val cal = Calendar.getInstance()
        cal.add(Calendar.SECOND, value)
        mEdit.putLong(PREF_EXPIRED_TIME, cal.timeInMillis)
        mEdit.apply()
    }

    fun isFirstTime(value: Boolean) {
        mEdit.putBoolean(PREF_FIRST_TIME, value)
        mEdit.apply()
    }

    companion object {
        var INSTANCE: Preferences? = null
        val instance: Preferences
            get() {
                if (INSTANCE == null) INSTANCE = Preferences()
                return INSTANCE as Preferences
            }
    }

    init {
        val app: Application = HaloKonsultanApplication.instance
        mPrefs = app.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        mEdit = mPrefs.edit()
    }
}