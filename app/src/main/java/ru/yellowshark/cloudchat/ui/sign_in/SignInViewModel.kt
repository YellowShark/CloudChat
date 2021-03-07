package ru.yellowshark.cloudchat.ui.sign_in

import androidx.lifecycle.ViewModel
import ru.yellowshark.cloudchat.domain.repository.Repository

class SignInViewModel(
    private val repository: Repository
) : ViewModel() {
    fun addNewUser() = repository.addNewUser()
}