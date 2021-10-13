package com.udacity.extensions


fun String.isValidURL() = trim().isNotEmpty()
        && (contains("http://") || contains("https://"))