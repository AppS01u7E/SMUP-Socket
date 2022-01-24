package com.appsolute.smupchat.domain.chat.repository

import com.appsolute.smupchat.domain.chat.data.entity.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository: JpaRepository<ChatRoom, String> {


}