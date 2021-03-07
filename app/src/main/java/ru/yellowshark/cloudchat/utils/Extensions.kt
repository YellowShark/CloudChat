package ru.yellowshark.cloudchat.utils

import android.app.Activity

fun Activity.restartActivity() {
    this.finish()
    startActivity(this.intent)
}