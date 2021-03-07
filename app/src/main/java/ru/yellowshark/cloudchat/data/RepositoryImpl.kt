package ru.yellowshark.cloudchat.data

import android.util.Log
import com.onesignal.OneSignal
import com.onesignal.OneSignal.PostNotificationResponseHandler
import org.json.JSONException
import org.json.JSONObject
import ru.yellowshark.cloudchat.data.FirebaseHelper.allUsers
import ru.yellowshark.cloudchat.domain.models.Message
import ru.yellowshark.cloudchat.domain.models.User
import ru.yellowshark.cloudchat.domain.repository.Repository
import ru.yellowshark.cloudchat.utils.ANONYMOUS
import java.util.*


private const val TAG = "RepositoryImpl"

class RepositoryImpl(
    private val prefs: SharedPrefsStorage,
) : Repository {
    override fun didLogin(): Boolean {
        val user = FirebaseHelper.currentUser
        Log.d(TAG, "hasAuthed: $user")
        return user != null
    }

    override fun sendPublicMessage(text: String) {
        val message = Message(
            text = text,
            author = FirebaseHelper.currentUser?.displayName ?: "Anonymous",
            userId = FirebaseHelper.currentUserId,
            time = Date().time,
            timeZoneId = TimeZone.getDefault().id
        )
        FirebaseHelper.messagesRef.push().setValue(message)
        sendPublicNotification(message.author, text)
    }

    private fun sendPublicNotification(author: String, text: String) {
        try {
            Log.d("USERS", "playerIds: ${allUsers.substring(0, allUsers.length - 1)}")
            OneSignal.postNotification(JSONObject("{" +
                    "'include_player_ids': [${allUsers.substring(0, allUsers.length - 1)}]," +
                    "'contents': {'en':'$text'}," +
                    "'headings': {'en': '$author'}," +
                    "'data': {'foo': 'bar'}" +
                    "}"),
                object : PostNotificationResponseHandler {
                    override fun onSuccess(response: JSONObject) {
                        Log.i("OneSignalExample", "postNotification Success: $response")
                    }

                    override fun onFailure(response: JSONObject) {
                        Log.e("OneSignalExample", "postNotification Failure: $response")
                    }
                })
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun addNewUser() {
        val newUser = User(
            userId = FirebaseHelper.currentUserId,
            playerId = prefs.getPlayerId(),
            name = FirebaseHelper.currentUser?.displayName ?: ANONYMOUS
        )
        FirebaseHelper.usersRef.child(FirebaseHelper.currentUserId).setValue(newUser)
    }
}