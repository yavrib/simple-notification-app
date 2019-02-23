package com.example.notifyme

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

class NotificationUtils {
    fun setNotification(timeInMilliSeconds: Long, activity: Activity) {
        println("setNotification")
        if (timeInMilliSeconds < 0) {
            return
        }

        println(timeInMilliSeconds)
        val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(activity.applicationContext, Notification::class.java)

        alarmIntent.putExtra("reason", "notification")
        alarmIntent.putExtra("timeStamp", timeInMilliSeconds)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMilliSeconds

        val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}