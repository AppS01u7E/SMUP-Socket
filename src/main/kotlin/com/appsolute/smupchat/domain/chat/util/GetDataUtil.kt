package com.appsolute.smupchat.domain.chat.util

import com.appsolute.smupchat.domain.chat.data.entity.ChatRoom
import com.appsolute.smupchat.domain.chat.error.base.ErrorCode
import com.appsolute.smupchat.domain.chat.error.base.ExceptionResponse
import com.appsolute.smupchat.domain.chat.error.service.ExceptionHandler
import com.appsolute.smupchat.domain.chat.repository.ChatRoomRepository
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.account.repository.UserRepository
import com.appsolute.smupchat.domain.soom.data.entity.Soom
import com.appsolute.smupchat.domain.soom.repository.group.GroupRepository
import com.corundumstudio.socketio.SocketIOClient
import org.hibernate.exception.DataException
import org.springframework.stereotype.Component

@Component
class GetDataUtil(
    private val exceptionHandler: ExceptionHandler,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val chatRoomRepository: ChatRoomRepository
) {

    fun findUser(client: SocketIOClient): User {
        val userId = client.get<String>("user")
        val user: User? = userRepository.findById(userId).orElse(null)
        user?: let{
            exceptionHandler.errorAndDisconnected(client, ExceptionResponse(ErrorCode.USER_NOT_FOUND))
        }
        return user!!
    }

    fun findGroup(groupId: String): Soom?{
        return findGroup(groupId, null)
    }


    fun findGroup(groupId: String, header: User?): Soom?{
        var soom: Soom? = null
        try {
            header?.let {
                soom = groupRepository.findByIdAndHeader(groupId, header).orElse(null)
            }?:let {
                soom = groupRepository.findById(groupId).orElse(null)
            }
        } catch (e: DataException){
            return null
        }
        return soom
    }


    fun findChatRoom(chatRoomId: String): ChatRoom? {
        val chatRoom: ChatRoom
        try {
            chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null)
        } catch (e: DataException) {
            return null
        }
        return chatRoom
    }


}