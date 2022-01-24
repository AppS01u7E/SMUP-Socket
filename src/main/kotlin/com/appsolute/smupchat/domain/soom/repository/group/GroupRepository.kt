package com.appsolute.smupchat.domain.soom.repository.group

import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.soom.data.entity.Soom
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupRepository: JpaRepository<Soom, String> {

    fun findAllByNameContaining(name: String): List<Soom>?
    fun findByIdAndMemberListContains(id: String, member: User): Optional<Soom>
    fun findByIdAndHeader(groupId: String, member: User): Optional<Soom>

}