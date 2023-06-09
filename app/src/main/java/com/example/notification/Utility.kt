package com.example.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.TaskStackBuilder

internal object Utility {

    fun getUriPendingIntent(
        context: Context,
        pendingIntentId: Int,
        uri: String,
    ): PendingIntent {
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


//    fun getUriPendingIntent(context: Context, requestCode: Int, uriString: String, flags: Int): PendingIntent {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
//        return PendingIntent.getActivity(context, requestCode, intent, flags)
//    }
//
//    fun getBroadcastPendingIntent(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent {
//        return PendingIntent.getBroadcast(context, requestCode, intent, flags)
//    }

//    fun getUriPendingIntentWithFlags(context: Context, requestCode: Int, uri: String, flags: Int): PendingIntent {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//        return PendingIntent.getActivity(context, requestCode, intent, flags)
//    }

//    fun getMainActivityPendingIntent(context: Context, flags: Int): PendingIntent {
//        val intent = Intent(context, MainActivity::class.java)
//        return PendingIntent.getActivity(context, 0, intent, flags)
//    }


//    @SuppressLint("UnspecifiedImmutableFlag")
//    fun getUriPendingIntent(context: Context, requestCode: Int, uri: String, flags: Int, notificationId: Int): PendingIntent {
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//
//        // Создаем PendingIntent для запуска действия
//        val pendingIntent = PendingIntent.getActivity(
//            context,
//            requestCode,
//            intent,
//            PendingIntent.FLAG_CANCEL_CURRENT
//        )
//
//        // Создаем BroadcastReceiver для передачи действия в будущем
//        val broadcastReceiverIntent = Intent(context, NotificationReceiver::class.java).apply {
//            action = NotificationReceiver.ACTION_DISMISS_NOTIFICATION // установить действие "отмены" уведомления
//            putExtra(EXTRA_NOTIFICATION_ID, notificationId) // установить идентификатор уведомления
//        }
//
//        // Создаем PendingIntent для "отмены" уведомления
//        val dismissPendingIntent = PendingIntent.getBroadcast(
//            context,
//            requestCode,
//            broadcastReceiverIntent,
//            PendingIntent.FLAG_ONE_SHOT
//        )
//
//        // Создаем IntentFilter для фильтрации событий "отмены" уведомлений
//        val intentFilter = IntentFilter(NotificationReceiver.ACTION_DISMISS_NOTIFICATION)
//
//        // Регистрируем BroadcastReceiver для получения событий "отмены" уведомлений
//        context.registerReceiver(object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
//                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                val notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0)
//                notificationManager.cancel(notificationId)
//            }
//        }, intentFilter)
//
//        // Объединяем оба действия в одно PendingIntent
//        val actions: Array<PendingIntent> = arrayOf(dismissPendingIntent, pendingIntent)
//        return PendingIntent.getActivities(
//            context,
//            requestCode,
//            actions,
//            flags
//        )
//    }
}
