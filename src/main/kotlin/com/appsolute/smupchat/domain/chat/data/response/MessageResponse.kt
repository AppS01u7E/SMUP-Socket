package com.appsolute.smupchat.domain.chat.data.response

import com.appsolute.smupchat.domain.chat.data.type.MessageType
import java.time.LocalDateTime

data class MessageResponse (
    val messageId: String,
    val sender: Sender,
    val content: String,
    val sentAt: LocalDateTime,
    val chatRoomId: String,
    val messageType: MessageType,
    val isMine: Boolean,
    val isDeleted: Boolean

){
    data class Sender(
        val name: String,
        val id: String
    )
}
