package com.appsolute.smupchat.domain.account.data.entity.user


import com.appsolute.smupchat.domain.soom.data.entity.Soom
import com.appsolute.smupchat.domain.soom.data.type.GroupAuthType
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class GroupInfo(
    user: User,
    soom: Soom,
    auth: GroupAuthType
) {
    @Id
    var id = UUID.randomUUID().toString()
    @ManyToOne
    var user = user
    @OneToOne
    var group = soom

    var joinedAt = LocalDateTime.now()

    var auth = auth



    fun changeAuth(auth: GroupAuthType): GroupInfo {
        this.auth = auth
        return this
    }

    fun removeAuth(): GroupInfo {
        this.auth = GroupAuthType.MEMBER
        return this
    }


}