package com.example.notificationsample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

object NotifyHelper {
    private const val NOTIFICATION_ID = 0
    private const val CHANNEL_ID = "NOTIFICATION TEMPLATE"
    enum class NOTIFY_TYPE(num: Int) {
        BASIC(0), PICTURE(1)
    }

    fun notify(context: Context, title: String, message: String, type: NOTIFY_TYPE) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        createNotificationChannel(notificationManager)
        val builder = when(type) {
            NOTIFY_TYPE.BASIC -> getNotificationBuilder(context, title, message)
//            NOTIFY_TYPE.PICTURE -> getPictureNotificationBuilder(context, title, message, Bitmap())
            else -> NotificationCompat.Builder(context, CHANNEL_ID)
        }

        notificationManager.notify(
            NOTIFICATION_ID,
            builder.build()
        )
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "Test",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.GRAY
            enableVibration(true)
            description = "Notification"
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun getNotificationBuilder(context: Context, title: String, text: String) : NotificationCompat.Builder {
        val notificationPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle(title)
            setContentText(text)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentIntent(notificationPendingIntent)
            setAutoCancel(true)
        }
    }

    private fun getPictureNotificationBuilder(context: Context, title: String, text: String, bitmap: Bitmap) : NotificationCompat.Builder {
        val notificationPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle(title)
            setContentText(text)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentIntent(notificationPendingIntent)
            setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            setAutoCancel(true)
        }
    }
}