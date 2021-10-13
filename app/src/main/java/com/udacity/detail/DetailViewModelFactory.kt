package com.udacity.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.extensions.getDownloadManager
import com.udacity.extensions.getNotificationManager

class DetailViewModelFactory(
    private val context: Context,
    private val downloadId: Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                context.getDownloadManager(),
                context.getNotificationManager(),
                downloadId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}