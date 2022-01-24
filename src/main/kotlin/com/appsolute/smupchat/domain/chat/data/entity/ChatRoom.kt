package com.appsolute.smupchat.domain.chat.data.entity

import com.appsolute.smupchat.domain.chat.data.request.SendMessageRequest
import com.appsolute.smupchat.domain.chat.data.type.ChatRoomType
import com.appsolute.smupchat.domain.chat.data.type.InterviewResultType
import com.appsolute.smupchat.domain.chat.data.type.MessageType
import com.appsolute.smupchat.domain.chat.service.ChatService
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.soom.data.entity.Soom
import com.corundumstudio.socketio.SocketIOClient
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
class ChatRoom(
    id: String,
    name: String,
    type: ChatRoomType,
    profile: String?,
    memberList: MutableList<User>,
    admin: User,
    soom: Soom?

) {

    @Id
    val id: String = id

    private val name: String = name

    private val type: ChatRoomType = type

    private val profile: String? = profile
    @ManyToMany(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val memberList = memberList
    @ManyToOne(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val admin: User = admin
    @OneToMany(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val message: MutableList<Message> = ArrayList<Message>()
    @OneToOne
    private val soom: Soom? = soom

    private val createdAt: LocalDateTime = LocalDateTime.now()

    private var isDone: Boolean = false

    private var isAccept: Boolean = false

    @Transient
    private lateinit var chatService: ChatService


    //대화방 종료
    fun makeDone(client: SocketIOClient) {
        chatService.sendMessage(client,
            SendMessageRequest(
                "대화가 종료되었습니다.",
                this.id,
                MessageType.SYSTEM
            ).toString()
        )
        this.isDone = true

    }

    //면접 결과 통지. 후 대화방 종료
    fun sendInterviewResult(client: SocketIOClient, result: InterviewResultType) {
        chatService.sendMessage(client,
            SendMessageRequest(
                "면접 결과는 "+ result.getResult() + "입니다.",
                this.id,
                MessageType.SYSTEM
            ).toString()
        )
        this.isAccept = result.getBoolean()
        this.makeDone(client)
    }

    fun isMember(user: User): ChatRoom?{
        if (!this.memberList.contains(user)) return null
        return this
    }

    fun checkIsDone(): ChatRoom?{
        if (this.isDone) return null
        return this
    }

}