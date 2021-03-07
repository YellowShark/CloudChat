package ru.yellowshark.cloudchat.ui.chat

import androidx.lifecycle.ViewModel
import ru.yellowshark.cloudchat.domain.repository.Repository

class ChatViewModel(
    private val repository: Repository
) : ViewModel() {
    fun didLogin() = repository.didLogin()
    fun sendMessage(text: String) = repository.sendPublicMessage(text)
}