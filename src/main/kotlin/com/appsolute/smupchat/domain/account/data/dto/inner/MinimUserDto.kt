package com.appsolute.smupchat.domain.account.data.dto.inner

import com.appsolute.smupchat.global.school.type.SchoolType
import com.appsolute.smupchat.domain.account.data.entity.user.type.Gender
import com.appsolute.smupchat.domain.account.data.entity.user.type.Role

data class MinimUserDto (
    var id: String,
    var email: String,
    var firstName: String,
    var lastName: String,
    var gender: Gender,
    var birth: String,
    var schoolType: SchoolType,
    var userType: Role
)