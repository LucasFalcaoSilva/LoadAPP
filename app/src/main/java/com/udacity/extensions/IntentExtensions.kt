package com.udacity.extensions

import android.content.Intent
import android.os.Bundle

fun Intent?.getLongDefault(key: String) = this?.getLongExtra(key, -1) ?: -1

fun Bundle?.getLongDefault(key: String) = this?.getLong(key, -1) ?: -1