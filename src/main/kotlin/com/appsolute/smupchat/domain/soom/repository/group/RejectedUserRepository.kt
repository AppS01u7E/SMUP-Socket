package com.appsolute.smupchat.domain.soom.repository.group

import com.appsolute.smupchat.domain.soom.data.entity.RejectedUser
import org.springframework.data.repository.CrudRepository

interface RejectedUserRepository: CrudRepository<RejectedUser, String> {
}