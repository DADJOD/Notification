package com.example.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

class ReplyReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationManagerCompat.from(context).cancel(R.id.DIRECT_REPLY_NOTIFICATION_ID)

        val remoteInput = RemoteInput.getResultsFromIntent(intent)

        if (remoteInput != null) {
            val input = remoteInput.getCharSequence(/*context.resources.getString(R.string.KEY_TEXT_REPLY)*/ "key_text_reply")
            Toast.makeText(context, "Input $input", Toast.LENGTH_SHORT).show()
        }
    }
}