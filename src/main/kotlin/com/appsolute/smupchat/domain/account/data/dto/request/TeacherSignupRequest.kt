package com.appsolute.smupchat.domain.account.dto.request

import com.appsolute.smupchat.global.school.type.SchoolType
import com.appsolute.smupchat.domain.account.data.entity.user.type.Gender
import com.appsolute.smupchat.domain.account.data.entity.user.Teacher
import com.appsolute.smupchat.domain.account.data.entity.user.type.TeacherType

data class TeacherSignupRequest(
    val email: String,
    val firstName: String,
    val lastname: String,
    val gender: Gender,
    val birth: String,
    val password: String,
    val teacherType: TeacherType,
    val major: String

) {
    fun toTeacher(id: String, password: String, schoolType: SchoolType): Teacher {
        return Teacher(id, this.email, this.firstName, this.lastname, this.gender, this.birth, password, this.teacherType, this.major, schoolType)

    }
}
