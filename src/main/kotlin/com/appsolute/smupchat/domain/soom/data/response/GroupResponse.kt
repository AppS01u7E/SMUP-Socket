package com.appsolute.smupchat.domain.soom.data.response

import com.appsolute.smupchat.domain.account.data.dto.response.UserResponse
import com.appsolute.smupchat.domain.account.data.dto.response.TeacherResponse
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.soom.data.type.GroupType

data class GroupResponse(
    val id: String,
    val name: String,
    val description: String,
    val type: GroupType,
    val header: User,
    val subHeaderList: List<UserResponse>?,
    val profile: String?,
    val memberCount: Int,
    val memberList: List<UserResponse>,
    val teacher: TeacherResponse?
)