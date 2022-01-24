package com.appsolute.smupchat.domain.soom.repository.group


import com.appsolute.smupchat.domain.account.data.entity.user.GroupInfo
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.soom.data.entity.Soom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupInfoRepository: JpaRepository<GroupInfo, String> {

    fun findByGroupAndUser(soom: Soom, user: User): Optional<GroupInfo>
}