package id.example.simplenotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonNotification = findViewById<Button>(R.id.btnNotification)
        val notificationData = NotificationData(
            notificationId = NOTIFICATION_CHAT,
            channelId = NOTIFICATION_CHANNEL_ID,
            channelName = "Chat Notification",
            channelDescription = "Testing description for notification channel android",
            ctx = applicationContext,
            title = "Trigger",
            body = "lorem ipsum dolor sit amet"
        )
        buttonNotification.setOnClickListener {
            showNotification(notificationData)
        }

    }

    fun showNotification(notificationData: NotificationData) {
        val mNotificationManager: NotificationManager = notificationData.ctx
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val builder = NotificationCompat.Builder(notificationData.ctx, notificationData.channelId).apply {
            setContentTitle(notificationData.title)
            setContentText(notificationData.body)
            setSmallIcon(R.drawable.ic_baseline_notifications_24)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(false)
            setOngoing(false)
            setStyle(NotificationCompat.BigTextStyle())
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //  Starting in Android 8.0 (API level 26), all notifications must be assigned to a channel
            val channel = NotificationChannel(notificationData.channelId, notificationData.channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.apply {
                description = notificationData.channelDescription
            }
            builder.setChannelId(notificationData.channelId)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()

        mNotificationManager.notify(notificationData.notificationId, notification)
    }

    companion object {
        const val NOTIFICATION_CHAT = 100
        const val NOTIFICATION_CHANNEL_ID = "channel_chat"
    }

}