package com.appsolute.smupchat.domain.account.data.dto.inner

import com.appsolute.smupchat.domain.account.data.entity.user.type.Dept

data class MinimStudentDto (
    var user: MinimUserDto,
    var dept: Dept,
    var grade: Int,
    var classNum: Int,
    var number: Int,
    var ent: Int
)