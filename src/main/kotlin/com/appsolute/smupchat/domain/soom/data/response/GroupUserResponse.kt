package com.appsolute.smupchat.domain.soom.data.response

import com.appsolute.smupchat.domain.soom.data.type.GroupAuthType
import com.appsolute.smupchat.domain.account.data.dto.response.UserResponse
import com.appsolute.smupchat.domain.soom.data.entity.Post
import java.time.LocalDateTime

data class GroupUserResponse(
    var user: UserResponse,
    var joinedAt: LocalDateTime,
    var last10Notice: List<Post>,
    var last10Replies: List<Post>,
    var auth: GroupAuthType

)