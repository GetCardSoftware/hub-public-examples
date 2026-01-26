package com.getcard.hub.providers.example

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object Preferences {

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("app_prefs", MODE_PRIVATE)
    }

    fun saveBtDeviceAddress(address: String?) {
        prefs.edit().putString("bt_device_address", address).apply()
    }

    fun getBtDeviceAddress(): String? {
        return prefs.getString("bt_device_address", null)
    }
}