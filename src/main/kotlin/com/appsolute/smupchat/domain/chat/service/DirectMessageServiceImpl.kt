package com.appsolute.smupchat.domain.chat.service

import com.appsolute.smupchat.domain.chat.data.entity.ChatRoom
import com.appsolute.smupchat.domain.chat.data.request.SendDMRequest
import com.appsolute.smupchat.domain.chat.data.request.SendMessageRequest
import com.appsolute.smupchat.domain.chat.data.type.ChatRoomType
import com.appsolute.smupchat.domain.chat.error.base.ErrorCode
import com.appsolute.smupchat.domain.chat.error.base.ExceptionResponse
import com.appsolute.smupchat.domain.chat.error.service.ExceptionHandler
import com.appsolute.smupchat.domain.chat.repository.ChatRoomRepository
import com.appsolute.smupchat.domain.chat.repository.MessageRepository
import com.appsolute.smupchat.domain.chat.util.GetDataUtil
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.account.repository.UserRepository
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.exception.DataException
import org.springframework.stereotype.Service


@Service
class DirectMessageServiceImpl(
    private val getDataUtil: GetDataUtil,
    private val exceptionHandler: ExceptionHandler,
    private val server: SocketIOServer,
    private val messageRepository: MessageRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val userRepository: UserRepository,
    private val chatService: ChatService

): DirectMessageService {

    private val objectMapper = ObjectMapper()


    override fun sendDM(client: SocketIOClient, json: String) {
        val user = getDataUtil.findUser(client)
        val target: User
        val request: SendDMRequest

        try {
            request = objectMapper.readValue(json, SendDMRequest::class.java)
            target = userRepository.findById(request.targetId).orElse(null)
                ?: return exceptionHandler.errorAndDisconnected(client, ExceptionResponse(
                    ErrorCode.USER_NOT_FOUND
                ))
        } catch (e: DataException){
            return exceptionHandler.errorAndDisconnected(client, ExceptionResponse(
                ErrorCode.IMPORTING_DATA_ERROR
            ))
        }

        var memberList: MutableList<User> = ArrayList<User>()
        memberList.addAll(arrayOf(user, target))

        val chatRoom = chatRoomRepository.findById(user.id + target.id).orElse(null)
            ?: chatRoomRepository.findById(target.id + user.id).orElse(null)
            ?:ChatRoom(
                user.id + target.id,
                user.getLastName()+"->"+target.getLastName(),
                ChatRoomType.PERSONAL,
                null,
                memberList,
                user,
                null
            )

        chatRoomRepository.save(chatRoom)

        chatService.sendMessage(
            client,
            SendMessageRequest(
                request.message,
                chatRoom.id,
                request.messageType
            ).toString()
        )


    }

}