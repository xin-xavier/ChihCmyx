package com.chih.mecm.cmyx.app.notification

import android.app.NotificationManager
import android.app.RemoteInput
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.ActivityUtils
import com.chih.mecm.cmyx.MainActivity
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.notification.Notifications.Companion.instance
import timber.log.Timber
import java.util.*

class NotificationService : Service() {
    private var mContext: Context? = null
    private var mNM: NotificationManager? = null
    private var mIsLoved = false
    private var mIsPlaying = true
    private val mContent: MutableList<NotificationContentWrapper> = ArrayList()
    private var mCurrentPosition = 1
    override fun onCreate() {
        super.onCreate()
        mContext = this
        mNM = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        initNotificationContent()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action != null) {
            Timber.e("onStartCommand action = %s", intent.action)
            when (intent.action) {
                NotificationConstants.ACTION_SIMPLE -> ActivityUtils.startActivity(
                    MainActivity::class.java
                )
                NotificationConstants.ACTION_ACTION -> {
                }
                NotificationConstants.ACTION_REMOTE_INPUT -> {
                }
                NotificationConstants.ACTION_BIG_PICTURE_STYLE -> {
                }
                NotificationConstants.ACTION_BIG_TEXT_STYLE -> {
                }
                NotificationConstants.ACTION_INBOX_STYLE -> {
                }
                NotificationConstants.ACTION_MEDIA_STYLE -> dealWithActionMediaStyle(intent)
                NotificationConstants.ACTION_MESSAGING_STYLE -> {
                }
                NotificationConstants.ACTION_YES -> mNM!!.cancel(NotificationConstants.NOTIFICATION_ACTION)
                NotificationConstants.ACTION_NO -> mNM!!.cancel(NotificationConstants.NOTIFICATION_ACTION)
                NotificationConstants.ACTION_DELETE -> {
                }
                NotificationConstants.ACTION_REPLY -> dealWithActionReplay(intent)
                NotificationConstants.ACTION_PROGRESS -> {
                }
                ACTION_SEND_PROGRESS_NOTIFICATION -> dealWithActionSendProgressNotification()
                NotificationConstants.ACTION_CUSTOM_HEADS_UP_VIEW -> dealWithActionCustomHeadsUpView(
                    intent
                )
                NotificationConstants.ACTION_CUSTOM_VIEW -> {
                }
                NotificationConstants.ACTION_CUSTOM_VIEW_OPTIONS_LOVE -> {
                    instance!!.sendCustomViewNotification(
                        this,
                        mNM!!,
                        mContent[mCurrentPosition],
                        !mIsLoved,
                        mIsPlaying
                    )
                    mIsLoved = !mIsLoved
                }
                NotificationConstants.ACTION_CUSTOM_VIEW_OPTIONS_PRE -> {
                    --mCurrentPosition
                    instance!!.sendCustomViewNotification(
                        this,
                        mNM!!,
                        notificationContent,
                        mIsLoved,
                        mIsPlaying
                    )
                }
                NotificationConstants.ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE -> {
                    instance!!.sendCustomViewNotification(
                        this,
                        mNM!!,
                        mContent[mCurrentPosition],
                        mIsLoved,
                        !mIsPlaying
                    )
                    mIsPlaying = !mIsPlaying
                }
                NotificationConstants.ACTION_CUSTOM_VIEW_OPTIONS_NEXT -> {
                    ++mCurrentPosition
                    instance!!.sendCustomViewNotification(
                        this,
                        mNM!!,
                        notificationContent,
                        mIsLoved,
                        mIsPlaying
                    )
                }
                NotificationConstants.ACTION_CUSTOM_VIEW_OPTIONS_LYRICS -> {
                }
                NotificationConstants.ACTION_CUSTOM_VIEW_OPTIONS_CANCEL -> mNM!!.cancel(
                    NotificationConstants.NOTIFICATION_CUSTOM
                )
                else -> {
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun initNotificationContent() {
        mContent.clear()
        mContent.add(
            NotificationContentWrapper(
                BitmapFactory.decodeResource(
                    mContext!!.resources,
                    R.drawable.custom_view_picture_pre
                ), "远走高飞", "金志文"
            )
        )
        mContent.add(
            NotificationContentWrapper(
                BitmapFactory.decodeResource(
                    mContext!!.resources,
                    R.drawable.custom_view_picture_current
                ), "最美的期待", "周笔畅 - 最美的期待"
            )
        )
        mContent.add(
            NotificationContentWrapper(
                BitmapFactory.decodeResource(
                    mContext!!.resources,
                    R.drawable.custom_view_picture_next
                ), "你打不过我吧", "跟风超人"
            )
        )
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun dealWithActionMediaStyle(intent: Intent) {
        val option = intent.getStringExtra(NotificationConstants.EXTRA_OPTIONS)
        Log.i(TAG, "media option = $option")
        if (option == null) {
            return
        }
        when (option) {
            NotificationConstants.MEDIA_STYLE_ACTION_PAUSE -> instance!!.sendMediaStyleNotification(
                this,
                mNM!!,
                false
            )
            NotificationConstants.MEDIA_STYLE_ACTION_PLAY -> instance!!.sendMediaStyleNotification(
                this,
                mNM!!,
                true
            )
            NotificationConstants.MEDIA_STYLE_ACTION_NEXT -> {
            }
            NotificationConstants.MEDIA_STYLE_ACTION_DELETE -> mNM!!.cancel(NotificationConstants.NOTIFICATION_MEDIA_STYLE)
            else -> {
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private fun dealWithActionReplay(intent: Intent) {
        val result = RemoteInput.getResultsFromIntent(intent)
        if (result != null) {
            val content = result.getString(NotificationConstants.REMOTE_INPUT_RESULT_KEY)
            Log.i(TAG, "content = $content")
            mNM!!.cancel(NotificationConstants.NOTIFICATION_REMOTE_INPUT)
        }
    }

    private fun dealWithActionSendProgressNotification() {
        Thread {
            for (i in 0..100) {
                instance!!.sendProgressViewNotification(mContext, mNM!!, i)
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    private fun dealWithActionCustomHeadsUpView(intent: Intent) {
        val headsUpOption = intent.getStringExtra(NotificationConstants.EXTRA_OPTIONS)
        Timber.i("heads up option = %s", headsUpOption)
        if (headsUpOption == null) {
            return
        }
        when (headsUpOption) {
            NotificationConstants.ACTION_ANSWER -> mNM!!.cancel(NotificationConstants.NOTIFICATION_CUSTOM_HEADS_UP)
            NotificationConstants.ACTION_REJECT -> mNM!!.cancel(NotificationConstants.NOTIFICATION_CUSTOM_HEADS_UP)
            else -> {
            }
        }
    }

    private val notificationContent: NotificationContentWrapper
        get() {
            when (mCurrentPosition) {
                -1 -> mCurrentPosition = 2
                3 -> mCurrentPosition = 0
                else -> {
                }
            }
            return mContent[mCurrentPosition]
        }

    companion object {
        private const val TAG = "NotificationService"
        const val ACTION_SEND_PROGRESS_NOTIFICATION =
            "com.android.peter.notificationdemo.ACTION_SEND_PROGRESS_NOTIFICATION"
    }
}