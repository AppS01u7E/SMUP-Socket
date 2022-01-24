package com.appsolute.smupchat.global.security.service

import io.jsonwebtoken.Claims
import org.springframework.stereotype.Service


@Service
interface TokenProvider {

    fun decodeToken(token: String): String
    fun getSecretKey(): String
    fun getDataFromClaims(claims: Claims): String


}