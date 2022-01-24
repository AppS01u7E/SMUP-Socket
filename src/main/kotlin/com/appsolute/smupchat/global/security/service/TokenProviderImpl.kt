package com.appsolute.smupchat.global.security.service

import com.appsolute.smupchat.domain.chat.config.SocketProperty
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service


@Service
class TokenProviderImpl(
    private val socketProperty: SocketProperty
): TokenProvider {


    override fun decodeToken(token: String): String {
        val claims: Claims = Jwts.parser()
            .setSigningKey(getSecretKey())
            .parseClaimsJws(token)
            .getBody()
        return getDataFromClaims(claims)
    }

    override fun getSecretKey(): String {
        return socketProperty.secretKey
    }

    override fun getDataFromClaims(claims: Claims): String {
        return claims.get("userPk", String::class.java)
    }




}