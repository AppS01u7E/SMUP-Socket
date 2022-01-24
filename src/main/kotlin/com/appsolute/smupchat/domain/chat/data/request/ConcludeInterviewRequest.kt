package com.appsolute.smupchat.domain.chat.data.request

import com.appsolute.smupchat.domain.chat.data.type.InterviewResultType

data class ConcludeInterviewRequest (
        val message: String,
        val result: InterviewResultType,
        val groupId: String,
        val targetId: String
)
