package ru.yellowshark.cloudchat.ui.chat

import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import ru.yellowshark.cloudchat.domain.models.Message


class MessageAdapter(
    options: FirebaseRecyclerOptions<Message>,
) : FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int,
        msg: Message
    ) {
        holder.bind(msg)
    }
}