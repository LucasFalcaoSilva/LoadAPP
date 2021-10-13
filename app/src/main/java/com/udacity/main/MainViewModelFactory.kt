package com.udacity.main

import android.app.DownloadManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    private val downloadManager: DownloadManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(downloadManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}