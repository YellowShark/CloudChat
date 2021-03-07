package ru.yellowshark.cloudchat.app

import android.app.Application
import android.content.Intent
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.onesignal.OSNotificationAction.ActionType
import com.onesignal.OneSignal
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin
import ru.yellowshark.cloudchat.app.di.repositoryModule
import ru.yellowshark.cloudchat.app.di.sharedPrefsModule
import ru.yellowshark.cloudchat.app.di.viewModelsModule
import ru.yellowshark.cloudchat.data.FirebaseHelper
import ru.yellowshark.cloudchat.data.SharedPrefsStorage
import ru.yellowshark.cloudchat.ui.main.MainActivity
import ru.yellowshark.cloudchat.utils.ONE_SIGNAL_APP_ID


class App : Application() {
    private val prefs: SharedPrefsStorage by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initOneSignal()
        prefs.updatePLayerId(OneSignal.getDeviceState()?.userId)
        loadAllUsers()
    }

    private fun initKoin() {
        startKoin(
            applicationContext,
            listOf(viewModelsModule, repositoryModule, sharedPrefsModule)
        )
    }

    private fun initOneSignal() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONE_SIGNAL_APP_ID)
        OneSignal.setNotificationOpenedHandler { result ->
            val actionType: ActionType = result.action.type
            val data: JSONObject = result.notification.additionalData
            Log.d("OneSignal", "NotificationOpenedHandler data: $data")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)

            /*if (data != null) {
                customKey = data.optString("customkey", null)
                if (customKey != null) Log.e(
                    "OneSignalExample",
                    "customkey set with value: $customKey"
                )
            }*/

            if (actionType == ActionType.ActionTaken) {
                Log.d("OneSignal", "Button pressed with id: " + result.action.actionId)
            }
        }
    }

    private fun loadAllUsers() {
        FirebaseHelper.usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val playerId = ds.child("playerId").getValue(String::class.java)
                    Log.d("USERS", "$playerId ")
                    if (playerId != null && playerId != prefs.getPlayerId())
                        FirebaseHelper.allUsers.append("'$playerId',")
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
