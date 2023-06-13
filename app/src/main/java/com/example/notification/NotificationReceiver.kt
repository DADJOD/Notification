package com.example.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_DISMISS_NOTIFICATION = "action_close_notification"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_DISMISS_NOTIFICATION -> {
                val notificationManager = context?.getSystemService(NotificationManager::class.java)
                notificationManager?.cancel(R.id.LOUVRE_NOTIFICATION_ID)
            }
        }
    }
}