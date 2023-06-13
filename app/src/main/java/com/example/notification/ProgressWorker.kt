package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class ProgressWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val notificationManager =
            applicationContext.getSystemService(NotificationManager::class.java)
        val channelID = "PSid"
        val channelName = "PSname"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(android.R.drawable.presence_video_away)
            .setContentTitle("Загрузка")
            .setContentText("Загружаем картинку")

        for (i in 0 until 20) {
            Thread.sleep(500)
            builder.setProgress(100, i * 5, false)
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