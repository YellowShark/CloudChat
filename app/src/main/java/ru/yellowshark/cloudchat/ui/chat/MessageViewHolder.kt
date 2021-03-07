package ru.yellowshark.cloudchat.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.yellowshark.cloudchat.R
import ru.yellowshark.cloudchat.data.FirebaseHelper
import ru.yellowshark.cloudchat.databinding.MsgItemBinding
import ru.yellowshark.cloudchat.domain.models.Message
import java.text.SimpleDateFormat
import java.util.*

private fun Long.toDateFormat(): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val resultDate = Date(this)
    return simpleDateFormat.format(resultDate)
}

class MessageViewHolder(private val binding: MsgItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): MessageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: MsgItemBinding =
                DataBindingUtil.inflate(inflater, R.layout.msg_item, parent, false)
            return MessageViewHolder(binding)
        }
    }

    fun bind(message: Message) {
        with(binding) {
            text = message.text
            author = message.author
            isOut = message.userId == FirebaseHelper.currentUserId
            time = message.time.toDateFormat()
            imageUrl = message.avatarUrl
            executePendingBindings()
        }
    }
}