package com.chih.mecm.cmyx.app.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.chih.mecm.cmyx.app.api.ConstantPool

abstract class TachycardiaReceiver(var isRegisterReceiver: Boolean = false) : BroadcastReceiver() {

    fun registerReceiver(context: Context) {
        if (!isRegisterReceiver) {
            isRegisterReceiver = true
            val instance = LocalBroadcastManager.getInstance(context)
            val intentFilter = IntentFilter(ConstantPool.ACTION_CHAT_MESSAGE_RECEIVER)
            instance.registerReceiver(this, intentFilter)
        }
    }

    fun unregisterReceiver(context: Context) {
        if (isRegisterReceiver) {
            val instance = LocalBroadcastManager.getInstance(context)
            instance.unregisterReceiver(this)
        }
    }


}