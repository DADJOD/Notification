package com.example.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlin.math.PI

// import android.support.v7.app.NotificationCompat;


class MainActivity : AppCompatActivity() {

    private var numberOfMessages = 12
    private lateinit var manager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = NotificationManagerCompat.from(this)
    }

    // Самое простое уведомление: Маленькая иконка для статус бара, заголовок и содержание
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

        // Что будет выполнено при щелчке на уведомление
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

        // После выбора уведомления оно будет убрано из статус-бара
        builder.setAutoCancel(true)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(R.id.SIMPLE_NOTIFICATION_ID, builder.build())

    }

    // Сложное уведомление - содержит дополнительные кнопки по щелчку на которые запустятся другие PendingIntent
    fun complexNotification(view: View?) {

        val notificationManager = getSystemService(NotificationManager::class.java)

        // PendingIntent для запуска браузера
        val pBrowser =
            Utility.getUriPendingIntent(this, R.id.BROWSER_PENDING_ID, "https://www.louvre.fr/")

        // PendingIntent для запуска карты
        val pMap = Utility.getUriPendingIntent(this, R.id.MAP_PENDING_ID, "geo:48.8606,2.3376")
        val builder = doBuilder()

        builder
            .setSmallIcon(android.R.drawable.ic_input_add)
            .setContentTitle("Экскурсия в Лувр")
            .setContentText("Экскурсия начнется через 15 мин")

        // Акция - кнопка с иконкой, текстом и PendingIntent
        // Кнопка для открытия веб-сайта
        builder.addAction(
            android.R.drawable.ic_menu_view,
            "Открыть сайт Лувра",
            pBrowser
        )

        // Кнопка для открытия карты
        builder.addAction(
            android.R.drawable.ic_menu_mapmode,
            "Открыть карту Лувра",
            pMap
        )

        // Все равно необходим BroadcastReceiver -> onReceive()
//        builder.addAction(
//            android.R.drawable.ic_menu_close_clear_cancel,
//            "Закрыть",
//            notificationManager.cancel(R.id.LOUVRE_NOTIFICATION_ID)
//        )

//        builder.setAutoCancel(true)
//        manager.notify(R.id.LOUVRE_NOTIFICATION_ID, builder.build()) // u need add this -->     @SuppressLint("MissingPermission")

//        builder.setContentIntent(
//            Utility.getUriPendingIntent(this, R.id.MAIN_ACTIVITY_PENDING_ID, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_ONE_SHOT)
//        )

        val notification = builder.build()

        notificationManager.notify(R.id.LOUVRE_NOTIFICATION_ID, notification)
    }

    // Новый вид уведомлений - "Большая картинка" Будет полноэкранным (приоритет + звук) со стэком активностей
    fun bigPicture(view: View?) {
        vibrate()

        val task = TaskStackBuilder.create(this)
        task.addNextIntent(Intent(this, MainActivity::class.java))
        task.addNextIntent(Intent(this, A2::class.java))
        task.addNextIntent(Intent(this, A3::class.java))

        val pTask = task.getPendingIntent(
            R.id.TASK_PENDING_ID,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Вначале создадим стиль и определим его
        val style = NotificationCompat.BigPictureStyle()

        style.setBigContentTitle("Новые сообщения")
        style.setSummaryText("У вас ${++numberOfMessages} новых сообщений от Ленны")
        // Картинка из ресурсов
        style.bigPicture(BitmapFactory.decodeResource(resources, R.drawable.lenna))

        val channelIid = "my_channel_id"
        val channelName = "Channel for my app"
        val soundUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.bikeringbell)
//        val defaultVibrationPattern = longArrayOf(500, 200, 500, 200, 500)

        val builder = NotificationCompat.Builder(this, channelIid)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelDescription = "My channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelIid, channelName, importance)
            channel.description = channelDescription
            channel.enableVibration(true)
//            channel.vibrationPattern = defaultVibrationPattern
            channel.setSound(soundUri, null)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            builder.setChannelId(channelIid)
        }

//        val defaultVibrationPattern = longArrayOf(500, 200,500)

        builder
            .setSmallIcon(android.R.drawable.btn_radio)
            .setStyle(style)
            .setContentIntent(pTask)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setVibrate(defaultVibrationPattern)
            .setSound(soundUri)
//            .setDefaults(Notification.DEFAULT_ALL)

        val notification = builder.build()

//        manager.notify(R.id.BIG_PICTURE_NOTIFICATION_ID, builder.build())
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(R.id.BIG_PICTURE_NOTIFICATION_ID, notification)


        val inboxStyle = NotificationCompat.InboxStyle()
            .setBigContentTitle("Новые сообщения")
            .addLine("Сообщение от жены")
            .setSummaryText("+ еще 2 сообщения")
            .addLine("Сообщение от бывшей жены")
            .addLine("Сообщение от суда")

        builder
            .setStyle(inboxStyle)

        // MediaStyle - для проигрывания звука или видео
        // Чтобы сделать уведомление полноэкранным
        // нужно установить высокий приоритет +
        // должна быть или вибрация или звук
    }

    fun inboxStyle(view: View) {
        vibrate()

        val channelID = "my_channel_id"
        val channelName = "My Channel"

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Есть еще виды стилей
        // InboxStyle - 6 или 7 строк текста
        val inboxStyle = NotificationCompat.InboxStyle()
            .setBigContentTitle("Новые сообщения")
            .addLine("Сообщение от жены")
            .setSummaryText("+ еще 2 сообщения")
            .addLine("Сообщение от бывшей жены")
            .addLine("Сообщение от суда")

        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(android.R.drawable.ic_input_add)
            .setContentTitle("Новые сообщения")
            .setContentText("У вас 7 сообщений")
            .setStyle(inboxStyle)
            .setAutoCancel(true)

        val notification = builder.build()
        notificationManager.notify(R.id.INBOX_STYLE_NOTIFICATION_ID, notification)
    }

    //WorkManager
    fun progressWorker(view: View?) {
        val workRequest = OneTimeWorkRequest.Builder(ProgressWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    //JobIntentService
    fun progressService(view: View?) {
        val intent = Intent(this, ProgressService::class.java)
        ProgressService.startService(this, intent)
    }

    //IntentService
//    fun progressService(view: View?) {
//        val intent = Intent(this, ProgressService::class.java)
//        startService(intent)
//    }

    // Inline reply уведомление - можно ввести текст
    @SuppressLint("MissingPermission")
    fun inlineReply(view: View?) {

        val notificationManager = getSystemService(NotificationManager::class.java)

        val builder = doBuilder()

        val replyLabel = "Ответ"
        val remoteInput = RemoteInput.Builder( //resources.getString(R.string.KEY_TEXT_REPLY)
        "key_text_reply")
            .setLabel(replyLabel)
            .build()

        val intent = Intent(this, ReplyReceiver::class.java)

        val replyPendingIntent = PendingIntent.getBroadcast(
            this,
            R.id.DIRECT_REPLY_PENDING_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val replyAction = NotificationCompat.Action.Builder(
            android.R.drawable.ic_input_add,
            "Ответить",
            replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()

        builder
            .setSmallIcon(android.R.drawable.sym_action_email)
            .setContentTitle("Как насчет в кино ?")
            .setContentText("У меня тут выдалось свободное время")
            .addAction(replyAction)
            .build()

        notificationManager.notify(
            R.id.DIRECT_REPLY_NOTIFICATION_ID,
            builder.build()
        )
    }

    private fun vibrate() {
        val vibrator = ContextCompat.getSystemService(this, Vibrator::class.java)
        if (vibrator?.hasVibrator() == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val vibrationEffect =
                    VibrationEffect.createWaveform(longArrayOf(0, 400, 200, 400, 200, 400), -1)
                vibrator.vibrate(vibrationEffect)
            } else {
                // Для более старых версий Android можно использовать VIBRATE permission и использовать deprecated методы
                vibrator.vibrate(400)
            }
        }
    }

    // Удаление уведомление - требуется идентификатор, с которым оно было запущено
    fun simpleCancel(view: View?) {
        manager.cancel(R.id.SIMPLE_NOTIFICATION_ID)
    }

    private fun doBuilder(): NotificationCompat.Builder {
        val channelId = "my_channel_id"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel Name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)

            channel.description = "My channel description"
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
    }
}