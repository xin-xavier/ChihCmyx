package com.chih.mecm.cmyx.app.notification

import android.annotation.TargetApi
import android.app.Notification
import android.app.Notification.MediaStyle
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.chih.mecm.cmyx.MainActivity
import com.chih.mecm.cmyx.R
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_ACTION
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_ANSWER
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_BIG_PICTURE_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_BIG_TEXT_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_CUSTOM_HEADS_UP_VIEW
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_CUSTOM_VIEW_OPTIONS_CANCEL
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_CUSTOM_VIEW_OPTIONS_LOVE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_CUSTOM_VIEW_OPTIONS_LYRICS
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_CUSTOM_VIEW_OPTIONS_NEXT
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_CUSTOM_VIEW_OPTIONS_PRE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_DELETE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_INBOX_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_MEDIA_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_MESSAGING_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_NO
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_PROGRESS
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_REJECT
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_REMOTE_INPUT
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_REPLY
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_SIMPLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.ACTION_YES
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.EXTRA_OPTIONS
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.MEDIA_STYLE_ACTION_DELETE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.MEDIA_STYLE_ACTION_NEXT
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.MEDIA_STYLE_ACTION_PAUSE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.MEDIA_STYLE_ACTION_PLAY
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_ACTION
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_BIG_PICTURE_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_BIG_TEXT_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_CUSTOM
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_CUSTOM_HEADS_UP
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_INBOX_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_MEDIA_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_MESSAGING_STYLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_PROGRESS
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_REMOTE_INPUT
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.NOTIFICATION_SAMPLE
import com.chih.mecm.cmyx.app.notification.NotificationConstants.Companion.REMOTE_INPUT_RESULT_KEY

class Notifications private constructor() {
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun sendSimpleNotification(context: Context, nm: NotificationManager) {
        //创建点击通知时发送的广播
        val intent = Intent(context, NotificationService::class.java)
        intent.action = ACTION_SIMPLE
        val pi = PendingIntent.getService(context, 0, intent, 0)
        //创建删除通知时发送的广播
        val deleteIntent = Intent(context, NotificationService::class.java)
        deleteIntent.action = ACTION_DELETE
        val deletePendingIntent = PendingIntent.getService(context, 0, deleteIntent, 0)
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.LOW) //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_launcher_round) //设置通知标题
                .setContentTitle("Simple notification") //设置通知内容
                .setContentText("Demo for simple notification !") //设置点击通知后自动删除通知
                .setAutoCancel(true) //设置显示通知时间
                .setShowWhen(true) //设置通知右侧的大图标
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.ic_notifiation_big
                    )
                ) //设置点击通知时的响应事件
                .setContentIntent(pi) //设置删除通知时的响应事件
                .setDeleteIntent(deletePendingIntent)
        //发送通知
        nm.notify(NOTIFICATION_SAMPLE, nb.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun sendActionNotification(context: Context?, nm: NotificationManager) {
        //创建点击通知时发送的广播
        val intent = Intent(context, NotificationService::class.java)
        intent.action = ACTION_ACTION
        val pi = PendingIntent.getService(context, 0, intent, 0)
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.DEFAULT) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Action notification") //设置通知内容
                .setContentText("Demo for action notification !") //设置点击通知后自动删除通知
                .setAutoCancel(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi)
        //创建点击通知 YES 按钮时发送的广播
        val yesIntent = Intent(context, NotificationService::class.java)
        yesIntent.action = ACTION_YES
        val yesPendingIntent = PendingIntent.getService(context, 0, yesIntent, 0)
        val yesActionBuilder = Notification.Action.Builder(
            Icon.createWithResource("", R.drawable.ic_yes),
            "YES",
            yesPendingIntent
        )
            .build()
        //创建点击通知 NO 按钮时发送的广播
        val noIntent = Intent(context, NotificationService::class.java)
        noIntent.action = ACTION_NO
        val noPendingIntent = PendingIntent.getService(context, 0, noIntent, 0)
        val noActionBuilder = Notification.Action.Builder(
            Icon.createWithResource("", R.drawable.ic_no),
            "NO",
            noPendingIntent
        )
            .build()
        //为通知添加按钮
        nb.setActions(yesActionBuilder, noActionBuilder)
        //发送通知
        nm.notify(NOTIFICATION_ACTION, nb.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun sendRemoteInputNotification(context: Context?, nm: NotificationManager) {
        //创建点击通知时发送的广播
        val intent = Intent(context, NotificationService::class.java)
        intent.action = ACTION_REMOTE_INPUT
        val pi = PendingIntent.getService(context, 0, intent, 0)
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.IMPORTANCE) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Remote input notification") //设置通知内容
                .setContentText("Demo for remote input notification !") //设置点击通知后自动删除通知
                .setAutoCancel(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi)
        //创建带输入框的按钮
        val remoteInput = RemoteInput.Builder(REMOTE_INPUT_RESULT_KEY)
            .setLabel("Reply").build()
        val remoteInputIntent = Intent(context, NotificationService::class.java)
        remoteInputIntent.action = ACTION_REPLY
        val replyPendingIntent = PendingIntent.getService(context, 2, remoteInputIntent, 0)
        val replyAction = Notification.Action.Builder(
            Icon.createWithResource("", R.drawable.ic_reply),
            "Reply",
            replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()
        //为通知添加按钮
        nb.setActions(replyAction)
        //发送通知
        nm.notify(NOTIFICATION_REMOTE_INPUT, nb.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun sendBigPictureStyleNotification(context: Context, nm: NotificationManager) {
        //创建点击通知时发送的广播
        val intent = Intent(context, NotificationService::class.java)
        intent.action = ACTION_BIG_PICTURE_STYLE
        val pi = PendingIntent.getService(context, 0, intent, 0)
        //创建大视图样式
        val bigPictureStyle = Notification.BigPictureStyle()
            .setBigContentTitle("Big picture style notification ~ Expand title")
            .setSummaryText("Demo for big picture style notification ! ~ Expand summery")
            .bigPicture(BitmapFactory.decodeResource(context.resources, R.drawable.big_style_picture))
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.DEFAULT) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Big picture style notification") //设置通知内容
                .setContentText("Demo for big picture style notification !") //设置点击通知后自动删除通知
                .setAutoCancel(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi) //设置大视图样式通知
                .setStyle(bigPictureStyle)
        //发送通知
        nm.notify(NOTIFICATION_BIG_PICTURE_STYLE, nb.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun sendBigTextStyleNotification(context: Context?, nm: NotificationManager) {
        //创建点击通知时发送的广播
        val intent = Intent(context, NotificationService::class.java)
        intent.action = ACTION_BIG_TEXT_STYLE
        val pi = PendingIntent.getService(context, 0, intent, 0)
        //创建大文字样式
        val bigTextStyle = Notification.BigTextStyle()
            .setBigContentTitle("Big text style notification ~ Expand title")
            .setSummaryText("Demo for big text style notification ! ~ Expand summery")
            .bigText(
                """
    We are the champions   
    We are the champions   
    No time for losers   
    Cause we are the champions of the World
    """.trimIndent()
            )
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.DEFAULT) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Big text style notification") //设置通知内容
                .setContentText("Demo for big text style notification !") //设置点击通知后自动删除通知
                .setAutoCancel(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi) //设置大文字样式通知
                .setStyle(bigTextStyle)
        //发送通知
        nm.notify(NOTIFICATION_BIG_TEXT_STYLE, nb.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun sendInboxStyleNotification(context: Context?, nm: NotificationManager) {
        //创建点击通知时发送的广播
        val intent = Intent(context, NotificationService::class.java)
        intent.action = ACTION_INBOX_STYLE
        val pi = PendingIntent.getService(context, 0, intent, 0)
        //创建信箱样式
        val inboxStyle = Notification.InboxStyle()
            .setBigContentTitle("Inbox style notification ~ Expand title")
            .setSummaryText("Demo for inbox style notification ! ~ Expand summery") //最多六行
            .addLine("1. I am email content.")
            .addLine("2. I am email content.")
            .addLine("3. I am email content.")
            .addLine("4. I am email content.")
            .addLine("5. I am email content.")
            .addLine("6. I am email content.")
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.DEFAULT) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Inbox style notification") //设置通知内容
                .setContentText("Demo for inbox style notification !") //设置点击通知后自动删除通知
                .setAutoCancel(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi) //设置信箱样式通知
                .setStyle(inboxStyle)
        //发送通知
        nm.notify(NOTIFICATION_INBOX_STYLE, nb.build())
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun sendMediaStyleNotification(context: Context?, nm: NotificationManager, isPlaying: Boolean) {
        //创建点击通知时发送的广播
        val intent = Intent(context, NotificationService::class.java)
        intent.action = ACTION_MEDIA_STYLE
        val pi = PendingIntent.getService(context, 0, intent, 0)
        //创建Action按钮
        val playOrPauseIntent = Intent(context, NotificationService::class.java)
        playOrPauseIntent.action = ACTION_MEDIA_STYLE
        playOrPauseIntent.putExtra(
            EXTRA_OPTIONS,
            if (isPlaying) MEDIA_STYLE_ACTION_PAUSE else MEDIA_STYLE_ACTION_PLAY
        )
        val playOrPausePendingIntent = PendingIntent.getService(
            context,
            0,
            playOrPauseIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val playOrPauseAction: Notification.Action = Notification.Action.Builder(
            Icon.createWithResource(
                context,
                if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
            ),
            if (isPlaying) "PAUSE" else "PLAY",
            playOrPausePendingIntent
        )
            .build()
        val nextIntent = Intent(context, NotificationService::class.java)
        nextIntent.action = ACTION_MEDIA_STYLE
        nextIntent.putExtra(EXTRA_OPTIONS, MEDIA_STYLE_ACTION_NEXT)
        val nextPendingIntent =
            PendingIntent.getService(context, 1, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val nextAction = Notification.Action.Builder(
            Icon.createWithResource(context, R.drawable.ic_next),
            "Next",
            nextPendingIntent
        )
            .build()
        val deleteIntent = Intent(context, NotificationService::class.java)
        deleteIntent.action = ACTION_MEDIA_STYLE
        deleteIntent.putExtra(EXTRA_OPTIONS, MEDIA_STYLE_ACTION_DELETE)
        val deletePendingIntent =
            PendingIntent.getService(context, 2, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val deleteAction = Notification.Action.Builder(
            Icon.createWithResource(context, R.drawable.ic_delete),
            "Delete",
            deletePendingIntent
        )
            .build()
        //创建媒体样式
        val mediaStyle = MediaStyle() //最多三个Action
            .setShowActionsInCompactView(0, 1, 2)
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.MEDIA) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Media style notification") //设置通知内容
                .setContentText("Demo for media style notification !") //设置通知不可删除
                .setOngoing(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi) //设置Action按钮
                .setActions(playOrPauseAction, nextAction, deleteAction) //设置信箱样式通知
                .setStyle(mediaStyle)
        //发送通知
        nm.notify(NOTIFICATION_MEDIA_STYLE, nb.build())
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun sendMessagingStyleNotification(context: Context?, nm: NotificationManager) {
        //创建点击通知时发送的广播
        val intent = Intent(context, NotificationService::class.java)
        intent.action = ACTION_MESSAGING_STYLE
        val pi = PendingIntent.getService(context, 0, intent, 0)
        //创建信息样式
        val messagingStyle = Notification.MessagingStyle("peter")
            .setConversationTitle("Messaging style notification")
            .addMessage("This is a message for you", System.currentTimeMillis(), "peter")
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.DEFAULT) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Messaging style notification") //设置通知内容
                .setContentText("Demo for messaging style notification !") //设置点击通知后自动删除通知
                .setAutoCancel(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi) //设置信箱样式通知
                .setStyle(messagingStyle)
        //发送通知
        nm.notify(NOTIFICATION_MESSAGING_STYLE, nb.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun sendProgressViewNotification(context: Context?, nm: NotificationManager, progress: Int) {
        //创建点击通知时发送的广播
        val intent = Intent(context, NotificationService::class.java)
        intent.action = ACTION_PROGRESS
        val pi = PendingIntent.getService(context, 0, intent, 0)
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.LOW) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Downloading...") //设置通知内容
                .setContentText("$progress%") //设置通知不可删除
                .setOngoing(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi)
                .setProgress(100, progress, false)
        //发送通知
        nm.notify(NOTIFICATION_PROGRESS, nb.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun sendCustomHeadsUpViewNotification(context: Context, nm: NotificationManager) {
        //创建点击通知时发送的广播
        //  Intent intent = new Intent(context,LaunchActivity.class);
        val intent = Intent(context, MainActivity::class.java)
        val pi = PendingIntent.getActivity(context, 0, intent, 0)
        //创建自定义顶部提醒视图
        val answerIntent = Intent(context, NotificationService::class.java)
        answerIntent.action = ACTION_CUSTOM_HEADS_UP_VIEW
        answerIntent.putExtra(EXTRA_OPTIONS, ACTION_ANSWER)
        val answerPendingIntent =
            PendingIntent.getService(context, 0, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val rejectIntent = Intent(context, NotificationService::class.java)
        rejectIntent.action = ACTION_CUSTOM_HEADS_UP_VIEW
        rejectIntent.putExtra(EXTRA_OPTIONS, ACTION_REJECT)
        val rejectPendingIntent =
            PendingIntent.getService(context, 1, rejectIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val headsUpView = RemoteViews(context.packageName, R.layout.custom_heads_up_layout)
        headsUpView.setOnClickPendingIntent(R.id.iv_answer, answerPendingIntent)
        headsUpView.setOnClickPendingIntent(R.id.iv_reject, rejectPendingIntent)
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.CRITICAL) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Custom heads up notification") //设置通知内容
                .setContentText("Demo for custom heads up notification !") //设置点击通知后自动删除通知
                .setAutoCancel(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi) //设置全屏响应事件;
                .setFullScreenIntent(pi, true) //设置自定义顶部提醒视图
                .setCustomHeadsUpContentView(headsUpView)
        //发送通知
        nm.notify(NOTIFICATION_CUSTOM_HEADS_UP, nb.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun sendCustomViewNotification(
        context: Context,
        nm: NotificationManager,
        content: NotificationContentWrapper,
        isLoved: Boolean,
        isPlaying: Boolean
    ) {
        //创建点击通知时发送的广播
        val intent = Intent(context, MainActivity::class.java)
        val pi = PendingIntent.getActivity(context, 0, intent, 0)
        //创建各个按钮的点击响应广播
        val intentLove = Intent(context, NotificationService::class.java)
        intentLove.action = ACTION_CUSTOM_VIEW_OPTIONS_LOVE
        val piLove =
            PendingIntent.getService(context, 0, intentLove, PendingIntent.FLAG_UPDATE_CURRENT)
        val intentPre = Intent(context, NotificationService::class.java)
        intentPre.action = ACTION_CUSTOM_VIEW_OPTIONS_PRE
        val piPre =
            PendingIntent.getService(context, 0, intentPre, PendingIntent.FLAG_UPDATE_CURRENT)
        val intentPlayOrPause = Intent(context, NotificationService::class.java)
        intentPlayOrPause.action = ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE
        val piPlayOrPause = PendingIntent.getService(
            context,
            0,
            intentPlayOrPause,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val intentNext = Intent(context, NotificationService::class.java)
        intentNext.action = ACTION_CUSTOM_VIEW_OPTIONS_NEXT
        val piNext =
            PendingIntent.getService(context, 0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT)
        val intentLyrics = Intent(context, NotificationService::class.java)
        intentLyrics.action = ACTION_CUSTOM_VIEW_OPTIONS_LYRICS
        val piLyrics =
            PendingIntent.getService(context, 0, intentLyrics, PendingIntent.FLAG_UPDATE_CURRENT)
        val intentCancel = Intent(context, NotificationService::class.java)
        intentCancel.action = ACTION_CUSTOM_VIEW_OPTIONS_CANCEL
        val piCancel =
            PendingIntent.getService(context, 0, intentCancel, PendingIntent.FLAG_UPDATE_CURRENT)
        //创建自定义小视图
        val customView = RemoteViews(context.packageName, R.layout.custom_view_layout)
        customView.setImageViewBitmap(R.id.iv_content, content.bitmap)
        customView.setTextViewText(R.id.tv_title, content.title)
        customView.setTextViewText(R.id.tv_summery, content.summery)
        customView.setImageViewBitmap(
            R.id.iv_play_or_pause, BitmapFactory.decodeResource(
                context.resources,
                if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
            )
        )
        customView.setOnClickPendingIntent(R.id.iv_play_or_pause, piPlayOrPause)
        customView.setOnClickPendingIntent(R.id.iv_next, piNext)
        customView.setOnClickPendingIntent(R.id.iv_lyrics, piLyrics)
        customView.setOnClickPendingIntent(R.id.iv_cancel, piCancel)
        //创建自定义大视图
        val customBigView = RemoteViews(context.packageName, R.layout.custom_big_view_layout)
        customBigView.setImageViewBitmap(R.id.iv_content_big, content.bitmap)
        customBigView.setTextViewText(R.id.tv_title_big, content.title)
        customBigView.setTextViewText(R.id.tv_summery_big, content.summery)
        customBigView.setImageViewBitmap(
            R.id.iv_love_big, BitmapFactory.decodeResource(
                context.resources,
                if (isLoved) R.drawable.ic_loved else R.drawable.ic_love
            )
        )
        customBigView.setImageViewBitmap(
            R.id.iv_play_or_pause_big, BitmapFactory.decodeResource(
                context.resources,
                if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
            )
        )
        customBigView.setOnClickPendingIntent(R.id.iv_love_big, piLove)
        customBigView.setOnClickPendingIntent(R.id.iv_pre_big, piPre)
        customBigView.setOnClickPendingIntent(R.id.iv_play_or_pause_big, piPlayOrPause)
        customBigView.setOnClickPendingIntent(R.id.iv_next_big, piNext)
        customBigView.setOnClickPendingIntent(R.id.iv_lyrics_big, piLyrics)
        customBigView.setOnClickPendingIntent(R.id.iv_cancel_big, piCancel)
        //创建通知
        val nb: Notification.Builder =
            Notification.Builder(context, NotificationChannels.MEDIA) //设置通知左侧的小图标
                .setSmallIcon(R.drawable.ic_notification) //设置通知标题
                .setContentTitle("Custom notification") //设置通知内容
                .setContentText("Demo for custom notification !") //设置通知不可删除
                .setOngoing(true) //设置显示通知时间
                .setShowWhen(true) //设置点击通知时的响应事件
                .setContentIntent(pi) //设置自定义小视图
                .setCustomContentView(customView) //设置自定义大视图
                .setCustomBigContentView(customBigView)
        //发送通知
        nm.notify(NOTIFICATION_CUSTOM, nb.build())
    }

    fun clearAllNotification(nm: NotificationManager) {
        nm.cancelAll()
    }

    companion object {
        @Volatile
        private var sInstance: Notifications? = null
        val instance: Notifications?
            get() {
                if (sInstance == null) {
                    synchronized(Notifications::class.java) {
                        if (sInstance == null) {
                            sInstance = Notifications()
                        }
                    }
                }
                return sInstance
            }
    }
}