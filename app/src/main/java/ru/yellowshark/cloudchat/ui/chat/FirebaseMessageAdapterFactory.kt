package ru.yellowshark.cloudchat.ui.chat

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import ru.yellowshark.cloudchat.data.FirebaseHelper
import ru.yellowshark.cloudchat.domain.models.Message
import ru.yellowshark.cloudchat.utils.MESSAGES_CHILD

class FirebaseMessageAdapterFactory {
    companion object {
        fun create(): FirebaseRecyclerAdapter<Message, MessageViewHolder> {
            val parser = SnapshotParser<Message> { data ->
                val msg: Message? = data.getValue(Message::class.java)
                msg?.id = data.key!!
                return@SnapshotParser msg!!
            }

            val messages = FirebaseHelper.db.child(MESSAGES_CHILD)
            val options = FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(messages, parser)
                .build()
            return MessageAdapter(options)
        }
    }
}
