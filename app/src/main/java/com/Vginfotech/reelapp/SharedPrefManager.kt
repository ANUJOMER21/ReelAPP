package com.Vginfotech.reelapp

import android.content.Context
import android.content.SharedPreferences



class SharedPrefManager(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val PREF_NAME = "MyAppPrefs"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_NUMBER = "number"
    }

    fun isLogin(): Boolean = getUserId()!=null

    // Save User ID
    fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    // Retrieve User ID
    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    // Save Number
    fun saveNumber(number: String) {
        sharedPreferences.edit().putString(KEY_NUMBER, number).apply()
    }

    // Retrieve Number
    fun getNumber(): String? {
        return sharedPreferences.getString(KEY_NUMBER, null) // Default to 0 if not set
    }

    // Clear all saved data
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}