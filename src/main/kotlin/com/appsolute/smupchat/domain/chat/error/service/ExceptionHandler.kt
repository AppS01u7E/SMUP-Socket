package com.appsolute.smupchat.domain.chat.error.service

import com.appsolute.smupchat.domain.chat.error.base.ExceptionResponse
import com.corundumstudio.socketio.SocketIOClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ExceptionHandler(
) {
    private val log = LoggerFactory.getLogger(javaClass)


    fun errorAndDisconnected(client: SocketIOClient, exception: ExceptionResponse){
        client.sendEvent("error", exception)
        log.warn(exception.data)
        client.disconnect()
    }

}