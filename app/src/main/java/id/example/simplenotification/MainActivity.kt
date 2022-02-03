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

        prepareNotificationChannel(NOTIFICATION_CHANNEL_ID, "Chat Notification", "Testing description for notification channel android")

        var incrementNotificationID = 0

        val buttonNotification = findViewById<Button>(R.id.btnNotification)
        buttonNotification.setOnClickListener {

            // Update notification id jika ingin menampilkan notifikasi baru
            // Apabila tidak di update maka notifikasi baru akan menimpa notifikasi lama
            val notificationData = NotificationData(
                notificationId = NOTIFICATION_CHAT,
                ctx = applicationContext,
                title = "Trigger $incrementNotificationID",
                body = "lorem ipsum dolor sit amet $incrementNotificationID"
            )
            showNotification(notificationData)
            incrementNotificationID++
        }

    }

    fun prepareNotificationChannel(channelId: String, channelName: String, channelDesc: String) {
        val mNotificationManager: NotificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //  Starting in Android 8.0 (API level 26), all notifications must be assigned to a channel
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.apply {
                description = channelDesc
            }
            mNotificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(notificationData: NotificationData) {
        val mNotificationManager: NotificationManager = notificationData.ctx
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val builder = NotificationCompat.Builder(notificationData.ctx, NOTIFICATION_CHANNEL_ID).apply {
            setContentTitle(notificationData.title)
            setContentText(notificationData.body)
            setSmallIcon(R.drawable.ic_baseline_notifications_24)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setStyle(NotificationCompat.BigTextStyle())
        }

        val notification = builder.build()

        mNotificationManager.notify(notificationData.notificationId, notification)
    }

    companion object {
        const val NOTIFICATION_CHAT = 100
        const val NOTIFICATION_CHANNEL_ID = "channel_chat"
    }

}