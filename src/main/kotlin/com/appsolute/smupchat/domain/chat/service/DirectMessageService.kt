package com.appsolute.smupchat.domain.chat.service

import com.corundumstudio.socketio.SocketIOClient
import org.springframework.stereotype.Service


interface DirectMessageService {


    fun sendDM(client: SocketIOClient, json: String)


}