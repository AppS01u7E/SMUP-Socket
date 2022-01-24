package com.appsolute.smupchat.domain.account.data.dto.inner


import com.appsolute.smupchat.domain.account.data.entity.user.type.TeacherType


data class MinimTeacherDto (
    var user: MinimUserDto,
    var teacherType: TeacherType,
    var major: String

)