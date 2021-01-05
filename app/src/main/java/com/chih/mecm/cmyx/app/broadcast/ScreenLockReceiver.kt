package com.chih.mecm.cmyx.app.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import android.content.IntentFilter

abstract class ScreenLockReceiver(var isRegisterReceiver: Boolean = false) : BroadcastReceiver() {

    fun registerReceiver(context: Context) {
        if (!isRegisterReceiver) {
            isRegisterReceiver = true
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_SCREEN_OFF)
            filter.addAction(Intent.ACTION_SCREEN_ON)
            context.registerReceiver(this, filter)
        }
    }

    fun unRegisterReceiver(context: Context) {
        if (isRegisterReceiver) {
            isRegisterReceiver = false
            context.unregisterReceiver(this)
        }
    }


}