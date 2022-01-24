package com.appsolute.smupchat.domain.chat.service

import com.appsolute.smupchat.domain.chat.data.entity.ChatRoom
import com.appsolute.smupchat.domain.chat.data.entity.Message
import com.appsolute.smupchat.domain.chat.data.request.SendMessageRequest
import com.appsolute.smupchat.domain.chat.data.response.MessageResponse
import com.appsolute.smupchat.domain.chat.data.type.MessageType
import com.appsolute.smupchat.domain.chat.error.base.ErrorCode
import com.appsolute.smupchat.domain.chat.error.base.ExceptionResponse
import com.appsolute.smupchat.domain.chat.error.service.ExceptionHandler
import com.appsolute.smupchat.domain.chat.repository.MessageRepository
import com.appsolute.smupchat.domain.chat.util.GetDataUtil
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.exception.DataException
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl(
    private val getDataUtil: GetDataUtil,
    private val exceptionHandler: ExceptionHandler,
    private val server: SocketIOServer,
    private val messageRepository: MessageRepository

): ChatService {

    private val objectMapper: ObjectMapper = ObjectMapper()


    override fun sendMessage(client: SocketIOClient, json: String) {
        val user: User = getDataUtil.findUser(client)
        val request: SendMessageRequest

        try{
            request = objectMapper.readValue(json, SendMessageRequest::class.java)
            request.checkIsNotSystemMessage()?: return exceptionHandler.errorAndDisconnected(client, ExceptionResponse(
                    ErrorCode.LOW_AUTHENTICATION
                )
            )
        } catch (e: DataException){
            return exceptionHandler.errorAndDisconnected(client, ExceptionResponse(
                ErrorCode.IMPORTING_DATA_ERROR
            ))
        }
        val chatRoom: ChatRoom =
            (
                    getDataUtil.findChatRoom(request.chatRoomId)?.isMember(user)
                    ?: return exceptionHandler.errorAndDisconnected(
                        client,
                        ExceptionResponse(
                            ErrorCode.CHAT_ROOM_NOT_EXISTS
                        )
                    )
                ).checkIsDone()
                ?: return exceptionHandler.errorAndDisconnected(
                    client,
                    ExceptionResponse(
                        ErrorCode.CLOSED_CHATTING_ROOM
                    )
                )

        val message = Message(
            request.message,
            user,
            chatRoom,
            request.messageType
        )
        messageRepository.save(
            message
        )
        send(chatRoom.id, message.toMessageResponse(client))
    }


    private fun send(chatRoomId: String, response: MessageResponse){
        var event: String = "message"
        if (response.messageType.equals(MessageType.SYSTEM)) event = "info"
        server.getRoomOperations(chatRoomId).sendEvent(
            event,
            response
        )
    }

}