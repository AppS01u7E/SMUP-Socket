package com.appsolute.smupchat.domain.chat.data.request

import com.appsolute.smupchat.domain.chat.data.type.MessageType

data class SendMessageRequest (
    val message: String,
    val chatRoomId: String,
    val messageType: MessageType
){
    fun checkIsNotSystemMessage(): SendMessageRequest?{
        if (this.messageType == MessageType.SYSTEM) return null
        return this
    }
}
