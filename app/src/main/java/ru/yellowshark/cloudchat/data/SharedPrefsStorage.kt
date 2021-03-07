package ru.yellowshark.cloudchat.data

import android.content.Context
import ru.yellowshark.cloudchat.R

private const val PLAYER_ID_KEY = "PLAYER_ID_KEY"

class SharedPrefsStorage(
    private val context: Context
) {
    private val prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    fun updatePLayerId(id: String?) {
        val editor = prefs.edit()
        editor.putString(PLAYER_ID_KEY, id)
        editor.apply()
    }

    fun getPlayerId(): String = prefs.getString(PLAYER_ID_KEY, "") ?: ""
}