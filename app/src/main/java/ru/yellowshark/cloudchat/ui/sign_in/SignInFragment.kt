package ru.yellowshark.cloudchat.ui.sign_in

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.viewmodel.ext.android.viewModel
import ru.yellowshark.cloudchat.R
import ru.yellowshark.cloudchat.data.FirebaseHelper
import ru.yellowshark.cloudchat.databinding.FragmentSignInBinding
import ru.yellowshark.cloudchat.utils.restartActivity


private const val GOOGLE_SIGN_IN_CODE = 0

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode)
        {
            GOOGLE_SIGN_IN_CODE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    Log.w("TAG", "Google sign in failed", e)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        account!!.let { acct ->
            val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
            FirebaseHelper.auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (!task.isSuccessful) {
                        Log.w("TAG", "signInWithCredential", task.exception)
                        Toast.makeText(
                            requireActivity(), "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.addNewUser()
                        requireActivity().restartActivity()
                    }
                }
        }
    }

    private fun initListeners() {
        binding.signInButton.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val signInIntent: Intent = FirebaseHelper.signInClient(requireContext()).signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE)
    }
}