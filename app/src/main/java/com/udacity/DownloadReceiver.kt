package com.udacity

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.udacity.extensions.getLongDefault
import com.udacity.extensions.getNotificationManager
import com.udacity.extensions.sendDownloadNotification
import com.udacity.main.MainViewModel

class DownloadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
            context.getNotificationManager().sendDownloadNotification(
                intent.getLongDefault(DownloadManager.EXTRA_DOWNLOAD_ID),
                context, MainViewModel.CHANNEL_ID,
            )
        }
    }
}