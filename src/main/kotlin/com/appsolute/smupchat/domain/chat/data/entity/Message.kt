package com.appsolute.smupchat.domain.chat.data.entity

import com.appsolute.smupchat.domain.chat.data.response.MessageResponse
import com.appsolute.smupchat.domain.chat.data.type.MessageType
import com.appsolute.smupchat.domain.chat.util.GetDataUtil
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.corundumstudio.socketio.SocketIOClient
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient


@Entity
class Message(
    content: String,
    sender: User,
    chatRoom: ChatRoom,
    type: MessageType

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    private val content: String = content
    @ManyToOne(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val sender: User = sender
    @ManyToOne(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val chatRoom: ChatRoom = chatRoom

    private val type: MessageType = type

    private val sentAt: LocalDateTime = LocalDateTime.now()

    private val isDelete: Boolean = false

    @Transient
    private lateinit var getDataUtil: GetDataUtil

    fun toMessageResponse(client: SocketIOClient): MessageResponse{
        return MessageResponse(
            this.id.toString(),
            MessageResponse.Sender(
                sender.getLastName(),
                sender.id
            ),
            this.content,
            this.sentAt,
            this.chatRoom.id,
            this.type,
            getDataUtil.findUser(client) == sender,
            this.isDelete
        )
    }

}