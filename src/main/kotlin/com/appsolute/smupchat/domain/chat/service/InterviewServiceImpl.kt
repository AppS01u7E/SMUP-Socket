package com.appsolute.smupchat.domain.chat.service

import com.appsolute.smupchat.domain.chat.data.entity.ChatRoom
import com.appsolute.smupchat.domain.chat.data.request.ApplyInterviewRequest
import com.appsolute.smupchat.domain.chat.data.request.ConcludeInterviewRequest
import com.appsolute.smupchat.domain.chat.data.request.SendMessageRequest
import com.appsolute.smupchat.domain.chat.data.type.ChatRoomType
import com.appsolute.smupchat.domain.chat.data.type.MessageType
import com.appsolute.smupchat.domain.chat.error.base.ErrorCode
import com.appsolute.smupchat.domain.chat.error.base.ExceptionResponse
import com.appsolute.smupchat.domain.chat.error.service.ExceptionHandler
import com.appsolute.smupchat.domain.chat.repository.ChatRoomRepository
import com.appsolute.smupchat.domain.chat.util.GetDataUtil
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.soom.data.entity.Soom
import com.appsolute.smupchat.domain.soom.repository.group.GroupRepository
import com.corundumstudio.socketio.SocketIOClient
import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import kotlin.collections.ArrayList


@Service
class InterviewServiceImpl(
    private val getDataUtil: GetDataUtil,
    private val exception: ExceptionHandler,
    private val objectMapper: ObjectMapper,
    private val groupRepository: GroupRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val messageService: ChatService

): InterviewService {

    override fun applyInterview(client: SocketIOClient, json: String) {
        val user: User = getDataUtil.findUser(client)
        val applyInterviewRequest: ApplyInterviewRequest
        val soom: Soom

        try {
            applyInterviewRequest = objectMapper.readValue(json, ApplyInterviewRequest::class.java)
            soom = getDataUtil.findGroup(applyInterviewRequest.groupId)?: return exception.errorAndDisconnected(client, ExceptionResponse(ErrorCode.GROUP_NOT_EXISTS))
        } catch (e: JacksonException){
            exception.errorAndDisconnected(client, ExceptionResponse(ErrorCode.JSON_PARSING_ERROR))
            return
        }
        val roomMembers: MutableList<User> = ArrayList<User>()
        roomMembers.addAll(soom.memberList)
        roomMembers.add(user)
        val chatRoomId: String = user.id + soom.id
        val chatRoom = ChatRoom(
            chatRoomId,
            soom.name + "Interview",
            ChatRoomType.INTERVIEW,
            soom.profile,
            roomMembers,
            soom.header,
            soom
        )

        chatRoomRepository.save(chatRoom)
        client.joinRoom(chatRoomId)
        messageService.sendMessage(client,
            SendMessageRequest(
                user.getLastName()+"님께서 면접을 신청하셨습니다.",
                soom.id,
                MessageType.SYSTEM
            ).toString()
        )

        return
    }

    override fun concludeInterview(client: SocketIOClient, json: String) {
        val user: User = getDataUtil.findUser(client)
        val request: ConcludeInterviewRequest
        val chatRoom: ChatRoom
        val soom: Soom

        try {
            request = objectMapper.readValue(json, ConcludeInterviewRequest::class.java)
            soom = getDataUtil.findGroup(request.groupId, user)?: return exception.errorAndDisconnected(client, ExceptionResponse(ErrorCode.LOW_AUTHENTICATION))
            chatRoom = getDataUtil.findChatRoom(request.targetId + soom.id)?: return exception.errorAndDisconnected(client, ExceptionResponse(ErrorCode.CHAT_ROOM_NOT_EXISTS))
        } catch (e: JacksonException){
            exception.errorAndDisconnected(client, ExceptionResponse(ErrorCode.JSON_PARSING_ERROR))
            return
        }

        chatRoom.sendInterviewResult(client, request.result)


    }

}