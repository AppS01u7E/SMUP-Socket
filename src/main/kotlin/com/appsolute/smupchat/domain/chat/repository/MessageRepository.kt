package com.appsolute.smupchat.domain.chat.repository

import com.appsolute.smupchat.domain.chat.data.entity.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository: JpaRepository<Message, Int> {


}