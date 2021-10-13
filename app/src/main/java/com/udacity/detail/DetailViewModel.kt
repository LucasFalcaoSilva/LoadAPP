package com.udacity.detail

import android.app.DownloadManager
import android.app.NotificationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.extensions.cancelNotification

class DetailViewModel(
    downloadManager: DownloadManager,
    notificationManager: NotificationManager,
    downloadId: Long
) : ViewModel() {

    private var _fileName = MutableLiveData<String>()
    val fileName: LiveData<String>
        get() = _fileName

    private var _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    init {
        Log.i("DetailViewModel", "MainViewModel created!")

        notificationManager.cancelNotification(downloadId.toInt())

        val cursor =
            downloadManager.query(DownloadManager.Query().setFilterById(downloadId))
        if (cursor.moveToFirst()) {
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            val uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI))

            _fileName.value = uri
            _status.value = if (DownloadManager.STATUS_SUCCESSFUL == status)
                "Success"
            else
                "Failed"
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("DetailViewModel", "MainViewModel destroyed!")
    }
}