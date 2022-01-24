package com.appsolute.smupchat.domain.chat.data.request

import com.appsolute.smupchat.domain.chat.data.type.MessageType

data class SendDMRequest(
    val message: String,
    val targetId: String,
    val messageType: MessageType

)
