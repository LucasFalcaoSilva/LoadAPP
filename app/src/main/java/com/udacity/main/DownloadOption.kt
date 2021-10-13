package com.udacity.main

import com.udacity.Constants

enum class DownloadOption(val url: String = "") {
    GLIDE(Constants.GLIDE_URL),
    RETROFIT(Constants.RETROFIT_URL),
    LOAD_APP(Constants.LOADAPP_URL),
    OTHERS,
    NO_OPTION;
}

fun DownloadOption.isNoOption() = this == DownloadOption.NO_OPTION

fun DownloadOption.isOthers() = this == DownloadOption.OTHERS