package com.appsolute.smupchat.domain.soom.repository.post

import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.soom.data.entity.Soom
import com.appsolute.smupchat.domain.soom.data.entity.Reply
import com.appsolute.smupchat.domain.soom.data.type.ReplyType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReplyRepository: JpaRepository<Reply, String> {

    fun findByIdAndWriter(id: String, writer: User): Optional<Reply>
    fun findAllByReplyTypeAndWriterAndGroup(replyType: ReplyType, member: User, soom: Soom): List<Reply>
}