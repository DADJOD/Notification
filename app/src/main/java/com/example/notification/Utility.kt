package com.example.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.TaskStackBuilder

internal object Utility {
    fun getUriPendingIntent(context: Context, pendingIntentId: Int, uri: String?): PendingIntent {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(uri)
        return PendingIntent.getActivity(
            context, pendingIntentId, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun getTaskStackPendingIntent(
        context: Context,
        pendingIntentId: Int,
        intents: List<Intent>,
    ): PendingIntent? {

        // Сформируем стэк активностей
        val task = TaskStackBuilder.create(context)
        for (i in intents) {
            task.addNextIntent(i)
        }
        return task.getPendingIntent(
            pendingIntentId,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
