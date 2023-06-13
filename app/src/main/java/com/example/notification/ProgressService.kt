package com.example.notification

import android.accessibilityservice.GestureDescription.Builder
import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters

class ProgressWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        val channelID = "PSid"
        val channelName = "PSname"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(android.R.drawable.presence_video_away)
            .setContentTitle("Загрузка")
            .setContentText("Загружаем картинку")

        for (i in 0 until 20) {
            Thread.sleep(500)
            builder.setProgress(100, i*5, false)
            notificationManager.notify(888, builder.build())
        }

        builder
            .setContentTitle("Загрузка завершена")
            .setContentText("Картинка загружена")
            .setProgress(0, 0, false)
        notificationManager.notify(888, builder.build())

        return Result.success()
    }
}



        //JobIntentService
//class ProgressService() : JobIntentService() {
//    companion object {
//        private const val JOB_ID = 123
//
//        fun startService(context: Context, intent: Intent) {
//            enqueueWork(context, ProgressService::class.java, JOB_ID, intent)
//        }
//    }
//
//    override fun onHandleWork(intent: Intent) {
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val channelID = "PSid"
//        val channelName = "PSname"
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        val builder = NotificationCompat.Builder(this, channelID)
//            .setSmallIcon(android.R.drawable.presence_video_away)
//            .setContentTitle("Загрузка")
//            .setContentText("Загружаем картинку")
//
//        for (i in 0 until 20) {
//            Thread.sleep(500)
//            builder.setProgress(100, i*5, false)
//            notificationManager.notify(888, builder.build())
//        }
//
//        builder
//            .setContentTitle("Загрузка завершена")
//            .setContentText("Картинка загружена")
//            .setProgress(0, 0, false)
//        notificationManager.notify(888, builder.build())
//    }
//}


        //IntentService
//class ProgressService() : IntentService("ProgressService") {
//    override fun onHandleIntent(intent: Intent?) {
//        val notificationManager = getSystemService(NotificationManager::class.java)
//        val channelID = "PSid"
//        val channelName = "PSname"
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        val builder = NotificationCompat.Builder(this, channelID)
//            .setSmallIcon(android.R.drawable.presence_video_away)
//            .setContentTitle("Загрузка")
//            .setContentText("Загружаем картинку")
//
//        for (i in 0 until 20) {
//            Thread.sleep(500)
//            builder.setProgress(100, i*5, false)
//            notificationManager.notify(888, builder.build())
//        }
//
//        builder
//            .setContentTitle("Загрузка завершена")
//            .setProgress(0, 0, false)
//        notificationManager.notify(888, builder.build())
//    }
//}