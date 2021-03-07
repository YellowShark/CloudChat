package ru.yellowshark.cloudchat.domain.repository

interface Repository {
    fun didLogin(): Boolean
    fun sendPublicMessage(text: String)
    fun addNewUser()
}