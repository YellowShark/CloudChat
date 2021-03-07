package ru.yellowshark.cloudchat.domain.models

data class Message(
    var id: String = "",
    val userId: String = "",
    val text: String = "",
    val author: String = "",
    val time: Long = 0L,
    val timeZoneId: String = "",
    val avatarUrl: String = "",
    val imageUrl: String = ""
)
