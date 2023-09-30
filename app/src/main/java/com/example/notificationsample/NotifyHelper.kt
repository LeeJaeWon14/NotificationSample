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
import kotlin.random.Random

object NotifyHelper {
    private const val CHANNEL_ID = "NOTIFICATION TEMPLATE"
    enum class NOTIFY_TYPE(num: Int) {
        BASIC(0), PICTURE(1), ACTION(2)
    }

    private const val GROUP = "NOTIFICATION TEST GROUP"
    private const val SUMMARY_ID = 10001
    private var NOTIFICATION_ID = 0

    fun notify(context: Context, title: String, message: String, type: NOTIFY_TYPE) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        createNotificationChannel(notificationManager)
        NOTIFICATION_ID = makeNotificationID()
        val builder = when(type) {
            NOTIFY_TYPE.BASIC -> getNotificationBuilder(context, title, message)
//            NOTIFY_TYPE.PICTURE -> getPictureNotificationBuilder(context, title, message, Bitmap())
            NOTIFY_TYPE.ACTION -> getActionNotificationBuilder(context, title, message)
            else -> NotificationCompat.Builder(context, CHANNEL_ID)
        }

        notificationManager.run {
            notify(
                NOTIFICATION_ID,
                builder.build()
            )
            notify(
                SUMMARY_ID,
                getSummaryNotificationBuilder(context, title, message).build()
            )
        }
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
            setGroup(GROUP)
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

    private fun getActionNotificationBuilder(context: Context, title: String, text: String) : NotificationCompat.Builder {
        val mIntent = Intent(context, MainActivity::class.java).apply { putExtra("action", true) }
        val pendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            mIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return getNotificationBuilder(context, title, text)
                .addAction(R.drawable.ic_launcher_foreground, "ACTION!", pendingIntent)
    }

    private fun getSummaryNotificationBuilder(context: Context, title: String, text: String) : NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setGroup(GROUP)
            .setGroupSummary(true)
    }

    private fun makeNotificationID() = Random.nextInt(9999)
}