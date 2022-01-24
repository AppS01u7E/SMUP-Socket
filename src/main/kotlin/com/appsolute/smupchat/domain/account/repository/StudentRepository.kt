package com.appsolute.smupchat.domain.account.repository

import com.appsolute.smupchat.domain.account.data.entity.user.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository: JpaRepository<Student, String> {

}