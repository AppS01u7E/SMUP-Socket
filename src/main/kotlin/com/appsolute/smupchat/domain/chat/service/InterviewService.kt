package com.appsolute.smupchat.domain.chat.service

import com.corundumstudio.socketio.SocketIOClient
import org.springframework.stereotype.Service


interface InterviewService {

    fun applyInterview(client: SocketIOClient, json: String)
    fun concludeInterview(client: SocketIOClient, json: String)


}