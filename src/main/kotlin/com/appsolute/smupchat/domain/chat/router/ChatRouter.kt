package com.appsolute.smupchat.domain.chat.router

import com.appsolute.smupchat.domain.chat.service.ChatService
import com.appsolute.smupchat.domain.chat.service.ConnectService
import com.appsolute.smupchat.domain.chat.service.DirectMessageService
import com.appsolute.smupchat.domain.chat.service.InterviewService
import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class ChatRouter(
    private val server: SocketIOServer,
    private val chatService: ChatService,
    private val socketService: ConnectService,
    private val directMessageService: DirectMessageService,
    private val interviewService: InterviewService
) {


    @PostConstruct
    fun router() {
        server.addConnectListener(socketService::connect)
        server.addDisconnectListener(socketService::disconnect)
        server.addEventListener("applyInterview", String::class.java){
                client: SocketIOClient, data: String, ackSender: AckRequest ->
            interviewService.applyInterview(
                client,
                data
            )
        }

        server.addEventListener("closeInterview", String::class.java){
                client: SocketIOClient, data: String, ackSender: AckRequest ->
            interviewService.concludeInterview(
                client,
                data
            )
        }

        server.addEventListener("sendDM", String::class.java){
                client: SocketIOClient, data: String, ackSender: AckRequest ->
            directMessageService.sendDM(
                client,
                data
            )
        }

        server.addEventListener("sendMessage", String::class.java){
            client: SocketIOClient, data: String, ackSender: AckRequest ->
            chatService.sendMessage(
                client,
                data
            )
        }

    }

}