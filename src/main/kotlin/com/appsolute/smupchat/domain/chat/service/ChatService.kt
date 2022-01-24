package com.appsolute.smupchat.domain.chat.service

import com.corundumstudio.socketio.SocketIOClient
import org.springframework.stereotype.Service


interface ChatService {

    fun sendMessage(client: SocketIOClient, json: String)


}