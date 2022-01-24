package com.appsolute.smupchat.domain.chat.service

import com.appsolute.smupchat.domain.chat.error.base.ErrorCode
import com.appsolute.smupchat.domain.chat.error.base.ExceptionResponse
import com.appsolute.smupchat.domain.chat.error.service.ExceptionHandler
import com.appsolute.smupchat.global.security.service.TokenProvider
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.account.repository.UserRepository
import com.corundumstudio.socketio.SocketIOClient
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.logging.SocketHandler


@Service
class ConnectServiceImpl(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository,
    private val exceptionHandler: ExceptionHandler

): ConnectService {

    private val objectMapper: ObjectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)


    override fun connect(client: SocketIOClient) {
        val token: String = client.handshakeData.getSingleUrlParam("authorization")
        val userId: String = tokenProvider.decodeToken(token)

        val user: User = userRepository.findById(userId).orElse(null)?: return exceptionHandler.errorAndDisconnected(
            client,
            ExceptionResponse(
                ErrorCode.USER_NOT_FOUND
            )
        )
        client.set("user", userId)
        log.info("connect: " + userId + "/" + user.getEmail() + "-> sessionId: " + client.sessionId)

        for (chatRoom in (user.getChatRoomList().filter { chatRoom -> chatRoom.checkIsDone() != null })) {
            client.joinRoom(chatRoom.id)
        }
    }



    override fun disconnect(client: SocketIOClient) {
        client.sendEvent("info", "success to disconnect")
        client.disconnect()
    }

}