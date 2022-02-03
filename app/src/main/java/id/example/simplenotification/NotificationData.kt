package id.example.simplenotification

import android.content.Context

data class NotificationData(

    val notificationId: Int = 101,

    val ctx: Context,

    val title: String,

    val body: String

)