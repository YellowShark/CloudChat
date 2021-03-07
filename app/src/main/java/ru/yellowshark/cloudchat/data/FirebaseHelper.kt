package ru.yellowshark.cloudchat.data

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import ru.yellowshark.cloudchat.R
import ru.yellowshark.cloudchat.utils.MESSAGES_CHILD
import ru.yellowshark.cloudchat.utils.USERS_CHILD
import java.lang.StringBuilder
import java.lang.ref.WeakReference

object FirebaseHelper {
    val db = FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()
    val messagesRef = db.child(MESSAGES_CHILD)
    val usersRef = db.child(USERS_CHILD)
    val allUsers = StringBuilder()

    val currentUser: FirebaseUser?
        get() = auth.currentUser
    val currentUserId: String
        get() = currentUser?.uid ?: ""

    fun signInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

}