package com.chih.mecm.cmyx.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.chih.mecm.cmyx.R
import java.util.*

object NotificationChannels {
    const val CRITICAL = "critical"
    const val IMPORTANCE = "importance"
    const val DEFAULT = "default"
    const val LOW = "low"
    const val MEDIA = "media"
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createAllNotificationChannels(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mediaChannel = NotificationChannel(
            MEDIA,
            context.getString(R.string.channel_media),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        mediaChannel.setSound(null, null)
        mediaChannel.vibrationPattern = null
        nm.createNotificationChannels(
            Arrays.asList(
                NotificationChannel(
                    CRITICAL,
                    context.getString(R.string.channel_critical),
                    NotificationManager.IMPORTANCE_HIGH
                ),
                NotificationChannel(
                    IMPORTANCE,
                    context.getString(R.string.channel_importance),
                    NotificationManager.IMPORTANCE_DEFAULT
                ),
                NotificationChannel(
                    DEFAULT,
                    context.getString(R.string.channel_default),
                    NotificationManager.IMPORTANCE_LOW
                ),
                NotificationChannel(
                    LOW,
                    context.getString(R.string.channel_low),
                    NotificationManager.IMPORTANCE_MIN
                ),  //custom notification channel
                mediaChannel
            )
        )
    }
}