package com.udacity.main

import android.app.DownloadManager
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.extensions.isValidURL

class MainViewModel(
    private val downloadManager: DownloadManager
) : ViewModel() {

    companion object {
        private const val APP_NAME = "LoadApp"
        const val CHANNEL_ID = "channelId"
        const val CHANNEL_NAME = "channelName"
    }

    var othersUrl = MutableLiveData<String>()

    private var downloadID: Long = 0
    private var downloadOption: DownloadOption = DownloadOption.NO_OPTION

    private var _eventHideOthers = MutableLiveData<Boolean>()
    val eventHideOthers: LiveData<Boolean>
        get() = _eventHideOthers

    private var _eventNoOption = MutableLiveData<Boolean>()
    val eventNoOption: LiveData<Boolean>
        get() = _eventNoOption

    private var _eventAnimateButton = MutableLiveData<Boolean>()
    val eventAnimateButton: LiveData<Boolean>
        get() = _eventAnimateButton

    private var _eventInvalidURL = MutableLiveData<Boolean>()
    val eventInvalidURL: LiveData<Boolean>
        get() = _eventInvalidURL

    init {
        Log.i("MainViewModel", "MainViewModel created!")
        _eventHideOthers.value = false
        _eventInvalidURL.value = false
        _eventAnimateButton.value = false
        othersUrl.value = ""
    }

    fun loadGlideURL() {
        downloadOption = DownloadOption.GLIDE
        _eventHideOthers.value = false
    }

    fun loadAppURL() {
        downloadOption = DownloadOption.LOAD_APP
        _eventHideOthers.value = false
    }

    fun loadRetrofitURL() {
        downloadOption = DownloadOption.RETROFIT
        _eventHideOthers.value = false
    }

    fun loadOthers() {
        downloadOption = DownloadOption.OTHERS
        _eventHideOthers.value = true
    }

    fun onEventNoOptionCompleted() {
        _eventNoOption.value = false
    }

    fun onEventInvalidURLCompleted() {
        _eventInvalidURL.value = false
    }

    fun onEventAnimatedButtonCompleted() {
        _eventAnimateButton.value = false
    }

    fun initDownload() {
        if (downloadOption.isNoOption()) {
            _eventNoOption.value = true
            return
        }
        if (downloadOption.isOthers() && othersUrl.value?.isValidURL()?.not() == true) {
            _eventInvalidURL.value = true
            return
        }
        _eventAnimateButton.value = true

        val request = DownloadManager.Request(getURI())
            .setTitle(APP_NAME)
            .setRequiresCharging(false)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    private fun getURI() = Uri.parse(
        downloadOption.let {
            if (it.isOthers())
                othersUrl.value
            else
                it.url
        })

    override fun onCleared() {
        super.onCleared()
        Log.i("MainViewModel", "MainViewModel destroyed!")
    }

}