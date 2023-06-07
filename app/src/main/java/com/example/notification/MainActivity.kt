package com.example.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

// import android.support.v7.app.NotificationCompat;


class MainActivity : AppCompatActivity() {

    private lateinit var manager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = NotificationManagerCompat.from(this)
    }

    // Самое простое уведомление:
    // Маленькая иконка для статус бара,
    // заголовок и содержание
    @SuppressLint("MissingPermission")
    fun simpleNotification(view: View?) {
        val builder = doBuilder()

        builder.setSmallIcon(android.R.drawable.ic_input_add)
        builder.setContentTitle("Something wrong")
        builder.setContentText("Something important has happened")

        builder.setLargeIcon(
            BitmapFactory.decodeResource(
                resources,
                R.drawable.beachpicture
            )
        )

        manager.notify(
            R.id.SIMPLE_NOTIFICATION_ID,
            builder.build()
        )
    }

    private fun doBuilder(): NotificationCompat.Builder {
        val channelId = "my_channel_id"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel Name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
        return builder
    }

    // Удаление уведомление -
    // требуется идентификатор, с которым оно
    // было запущено
    fun simpleCancel(view: View?) {
        manager.cancel(R.id.SIMPLE_NOTIFICATION_ID)
    }

    // Запуск браузера через уведомление
    @SuppressLint("MissingPermission")
    fun browserNotification(view: View?) {
        // Создание PendingIntent - "консерва" которую можно передать кому-то
        // чтобы кто-то другой (не наше приложение) выполнил этот интент

        val a2 = Intent(this, A2::class.java)
        val pA2 = PendingIntent.getActivity(
            this,
            333,
            a2,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Что будет выполнено при щелчке на
        // уведомление

        val builder = doBuilder()
        builder
            .setSmallIcon(android.R.drawable.ic_input_add)
            .setContentTitle("Заголовок уведомления")
            .setContentText("Текст уведомления")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pA2) // Добавляем PendingIntent в качестве действия при нажатии на уведомление

        builder.setLargeIcon(
            BitmapFactory.decodeResource(
                resources,
                R.drawable.beachpicture
            )
        )

        // После выбора уведомления оно будет убрано
        // из статус-бара
        builder.setAutoCancel(true)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(R.id.SIMPLE_NOTIFICATION_ID, builder.build())

    }

    // Сложное уведомление - содержит дополнительные кнопки
    // по щелчку на которые запустятся другие
    // PendingIntent
    fun complexNotification(view: View?) {

        // PendingIntent для запуска браузера

        // PendingIntent для запуска карты

        // Акция - кнопка с иконкой, текстом и
        // PendingIntent
    }

    private val numberOfMessages = 12

    // Новый вид уведомлений - "Большая картинка"
    // Будет полноэкранным (приоритет + звук)
    // Со стэком активностей
    fun bigPicture(view: View?) {


        // Вначале создадим стиль и определим его

        // Картинка из ресурсов

        // Есть еще виды стилей
        // MediaStyle - для проигрывания звука или видео
        // InboxStyle - 6 или 7 строк текста

        // Чтобы сделать уведомление полноэкранным
        // нужно установить высокий приоритет +
        // должна быть или вибрация или звук
    }

    // Собственный вид уведомления
    fun custom(view: View?) {

        // PendingIntent на запуск броузера
        val pIntent: PendingIntent? = null

        // Так как иерархия View не принадлежит
        // приложению, нужно использовать RemoteViews
        val remote = RemoteViews(
            packageName,
            R.layout.custom
        )

        // Так устанавливаются значения виджетов внутри RemoteViews
        remote.setImageViewResource(R.id.picture, R.mipmap.ic_launcher)
        remote.setTextViewText(R.id.text, "Текст с картинкой")
        remote.setOnClickPendingIntent(R.id.button, pIntent)
    }

    // Inline reply уведомление -
    // можно ввести текст
    @SuppressLint("MissingPermission")
    fun inlineReply(view: View?) {
        val replyLabel = "Ответ"
        val remoteInput = RemoteInput.Builder(
            resources.getString(R.string.KEY_TEXT_REPLY)
        )
            .setLabel(replyLabel)
            .build()
        val intent: Intent? = null
        val replyPendingIntent: PendingIntent? = null
        val replyAction = NotificationCompat.Action.Builder(
            android.R.drawable.btn_plus,
            "Ответить",
            replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()
        val newMessageNotification: Notification? = null
        NotificationManagerCompat.from(this).notify(
            R.id.DIRECT_REPLY_NOTIFICATION_ID,
            newMessageNotification!!
        )
    }
}















