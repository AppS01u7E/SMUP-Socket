package com.appsolute.smupchat.domain.account.repository

import com.appsolute.smupchat.domain.account.data.entity.user.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository: JpaRepository<Teacher, String> {
}