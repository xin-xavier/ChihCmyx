package com.chih.mecm.cmyx.app.notification

interface NotificationConstants {
    companion object{
        const val NOTIFICATION_SAMPLE = 0
        const val NOTIFICATION_ACTION = 1
        const val NOTIFICATION_REMOTE_INPUT = 2
        const val NOTIFICATION_BIG_PICTURE_STYLE = 3
        const val NOTIFICATION_BIG_TEXT_STYLE = 4
        const val NOTIFICATION_INBOX_STYLE = 5
        const val NOTIFICATION_MEDIA_STYLE = 6
        const val NOTIFICATION_MESSAGING_STYLE = 7
        const val NOTIFICATION_PROGRESS = 8
        const val NOTIFICATION_CUSTOM_HEADS_UP = 9
        const val NOTIFICATION_CUSTOM = 10

        const val ACTION_SIMPLE = "com.android.peter.notificationdemo.ACTION_SIMPLE"
        const val ACTION_ACTION = "com.android.peter.notificationdemo.ACTION_ACTION"
        const val ACTION_REMOTE_INPUT =
            "com.android.peter.notificationdemo.ACTION_REMOTE_INPUT"
        const val ACTION_BIG_PICTURE_STYLE =
            "com.android.peter.notificationdemo.ACTION_BIG_PICTURE_STYLE"
        const val ACTION_BIG_TEXT_STYLE =
            "com.android.peter.notificationdemo.ACTION_BIG_TEXT_STYLE"
        const val ACTION_INBOX_STYLE =
            "com.android.peter.notificationdemo.ACTION_INBOX_STYLE"
        const val ACTION_MEDIA_STYLE =
            "com.android.peter.notificationdemo.ACTION_MEDIA_STYLE"
        const val ACTION_MESSAGING_STYLE =
            "com.android.peter.notificationdemo.ACTION_MESSAGING_STYLE"
        const val ACTION_PROGRESS = "com.android.peter.notificationdemo.ACTION_PROGRESS"
        const val ACTION_CUSTOM_HEADS_UP_VIEW =
            "com.android.peter.notificationdemo.ACTION_CUSTOM_HEADS_UP_VIEW"
        const val ACTION_CUSTOM_VIEW =
            "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW"
        const val ACTION_CUSTOM_VIEW_OPTIONS_LOVE =
            "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_LOVE"
        const val ACTION_CUSTOM_VIEW_OPTIONS_PRE =
            "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_PRE"
        const val ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE =
            "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE"
        const val ACTION_CUSTOM_VIEW_OPTIONS_NEXT =
            "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_NEXT"
        const val ACTION_CUSTOM_VIEW_OPTIONS_LYRICS =
            "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_LYRICS"
        const val ACTION_CUSTOM_VIEW_OPTIONS_CANCEL =
            "com.android.peter.notificationdemo.ACTION_CUSTOM_VIEW_OPTIONS_CANCEL"

        const val ACTION_YES = "com.android.peter.notificationdemo.ACTION_YES"
        const val ACTION_NO = "com.android.peter.notificationdemo.ACTION_NO"
        const val ACTION_DELETE = "com.android.peter.notificationdemo.ACTION_DELETE"
        const val ACTION_REPLY = "com.android.peter.notificationdemo.ACTION_REPLY"
        const val REMOTE_INPUT_RESULT_KEY = "remote_input_content"

        const val EXTRA_OPTIONS = "options"
        const val MEDIA_STYLE_ACTION_DELETE = "action_delete"
        const val MEDIA_STYLE_ACTION_PLAY = "action_play"
        const val MEDIA_STYLE_ACTION_PAUSE = "action_pause"
        const val MEDIA_STYLE_ACTION_NEXT = "action_next"
        const val ACTION_ANSWER = "action_answer"
        const val ACTION_REJECT = "action_reject"
    }
}