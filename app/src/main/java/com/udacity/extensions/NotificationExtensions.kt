package com.udacity.extensions

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.udacity.R

fun NotificationManager.sendDownloadNotification(
    downloadId: Long,
    applicationContext: Context,
    channelId: String
) {
    val notificationId = downloadId.toInt()

    val contentIntent = applicationContext.getDetailIntent(downloadId)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        notificationId,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(applicationContext.getString(R.string.notification_description))
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .addAction(
            NotificationCompat.Action(
                0,
                applicationContext.getString(R.string.notification_button),
                contentPendingIntent
            )
        )

    notify(notificationId, builder.build())
}

fun NotificationManager.cancelNotification(id: Int) {
    cancel(id)
}