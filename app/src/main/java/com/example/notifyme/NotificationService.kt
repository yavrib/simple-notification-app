package com.example.notifyme

import android.annotation.SuppressLint
import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import java.util.*

class NotificationService : IntentService("NotificationService") {
    private lateinit var mNotification: android.app.Notification
    private val mNotificationId: Int = 1000

    companion object {
        const val CHANNEL_ID = "notifyme.example.com.CHANNEL_ID"
        const val CHANNEL_NAME = "Sample Notification"
    }

    @SuppressLint("NewApi")
    private fun createChannel() {
        val context = this.applicationContext
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        notificationChannel.enableVibration(true)
        notificationChannel.setShowBadge(true)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.parseColor("#8e44ad")
        notificationChannel.description = "Description"
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onHandleIntent(intent: Intent) {
        createChannel()

        var timestamp: Long = 0

        if (intent != null && intent.extras != null) {
            timestamp = intent.extras!!.getLong("timestamp")
        }

        if (timestamp <= 0) {
            return
        }

        val context = this.applicationContext
        var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyIntent = Intent(this, ResultActivity::class.java)
        val title = "Sample Notification"
        val message = "Hebele hübele"
        notifyIntent.putExtra("title", title)
        notifyIntent.putExtra("message", message)
        notifyIntent.putExtra("notification", true)

        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val calendar = Calendar.getInstance()

        calendar.timeInMillis = timestamp

        val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val res = this.resources
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        mNotification = android.app.Notification.Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setContentTitle(title)
            .setStyle(android.app.Notification.BigTextStyle().bigText(message))
            .setContentText(message)
            .build()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(mNotificationId, mNotification)
    }
}
