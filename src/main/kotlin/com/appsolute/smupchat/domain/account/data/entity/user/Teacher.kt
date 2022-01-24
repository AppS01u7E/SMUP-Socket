package com.appsolute.smupchat.domain.account.data.entity.user

import com.appsolute.smupchat.domain.account.data.dto.response.TeacherResponse
import com.appsolute.smupchat.domain.account.data.dto.inner.MinimTeacherDto
import com.appsolute.smupchat.domain.account.data.entity.user.type.Gender
import com.appsolute.smupchat.domain.account.data.entity.user.type.Role
import com.appsolute.smupchat.domain.account.data.entity.user.type.TeacherType
import com.appsolute.smupchat.global.school.type.SchoolType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


@Entity
@DiscriminatorValue("TEACHER")
class Teacher(
    id: String,
    email: String,
    firstName: String,
    lastName: String,
    gender: Gender,
    birth: String,
    password: String,
    teacherType: TeacherType,
    major: String,
    school: SchoolType
) : User(
    id,
    email,
    firstName,
    lastName,
    gender,
    birth,
    password,
    Role.TEACHER,
    school
) {
    private var teacherType = teacherType

    private var major = major


    fun toTeacherResponse(): TeacherResponse {
        return TeacherResponse(
            this.id,
            this.getEmail(),
            this.getFirstName(),
            this.getLastName(),
            this.getGender(),
            this.teacherType,
            this.major
        )
    }

    fun toMinimTeacherDto(): MinimTeacherDto{
        return MinimTeacherDto(
            this.toMinimUserDto(),
            this.teacherType,
            this.major
        )
    }

}