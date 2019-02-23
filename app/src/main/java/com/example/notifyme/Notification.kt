package com.example.notifyme

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = Intent(context, NotificationService::class.java)

        service.putExtra("reason", intent.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))

        context.startService(service)
    }
}