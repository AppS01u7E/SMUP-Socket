package com.appsolute.smupchat.domain.chat.error.base

class ExceptionResponse (
    errorCode: ErrorCode,

){
    val errorCode = errorCode
    val data = errorCode.message
}