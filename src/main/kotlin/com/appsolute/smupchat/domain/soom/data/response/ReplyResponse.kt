package com.appsolute.smupchat.domain.soom.data.response

import com.appsolute.smupchat.domain.account.data.dto.response.UserResponse
import com.appsolute.smupchat.domain.soom.data.type.ReplyType

data class ReplyResponse (
    val id: String,
    val content: String,
    val writer: UserResponse,
    val sendToId: String,
    val type: ReplyType
)