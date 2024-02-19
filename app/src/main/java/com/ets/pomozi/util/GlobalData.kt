package com.ets.pomozi.util

import android.content.Context
import com.ets.pomozi.R
import com.ets.pomozi.models.OrganizationModel
import com.ets.pomozi.models.RewardModel

object GlobalData {
    private var _token: String? = null

    fun loadTokenFromSharedPrefs(content: Context) {
        val sharedPrefs = content.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        _token = sharedPrefs.getString("token", null)
    }

    fun saveToken(content: Context, value: String?) {
        _token = value

        val sharedPrefs = content.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        if (value == null) {
            editor.remove("token")
        } else {
            editor.putString("token", value)
        }
        editor.apply()
    }

    fun setToken(value: String?) {
        _token = value
    }

    fun getToken(): String? {
        return _token
    }

    val API_PREFIX = "http://192.168.1.43:3000/api/"
    val PHOTO_PREFIX = "http://192.168.1.43:3000/storage/"
}
