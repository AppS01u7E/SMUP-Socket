package com.appsolute.smupchat.domain.account.repository

import com.appsolute.smupchat.domain.account.data.entity.user.GroupInfo
import com.appsolute.smupchat.domain.account.data.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface UserRepository: JpaRepository<User, String> {
    fun findByIdAndGroupInfoContaining(id: String, groupInfo: GroupInfo): Optional<User>

}