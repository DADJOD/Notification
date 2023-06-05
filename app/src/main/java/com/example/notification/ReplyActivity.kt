package com.example.notification

import android.annotation.SuppressLint
import android.app.RemoteInput
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ReplyActivity : AppCompatActivity() {
    private var request: TextView? = null
    private var reply: EditText? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reply)
        request = findViewById(R.id.request)
        reply = findViewById(R.id.reply_text)

        // Intent с помощью которого запущена активити
        val intent = intent

        // Получим содержимое inline reply
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val requestText =
                remoteInput.getCharSequence(resources.getString(R.string.KEY_TEXT_REPLY))
            // И запустим обработчик

            // ...
        }
    }

    // Для отправки ответа на inline reply уведомление
    fun reply(view: View?) {}
}