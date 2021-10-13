package com.udacity.extensions

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.udacity.detail.DetailActivity
import com.udacity.detail.DetailActivity.Companion.DOWNLOAD_ID

fun Context.showMessage(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.getDownloadManager() =
    getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager

fun Context.getNotificationManager() =
    getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

fun Context.getDetailIntent(downloadId: Long) =
    Intent(applicationContext, DetailActivity::class.java).apply {
        putExtra(DOWNLOAD_ID, downloadId)
    }
