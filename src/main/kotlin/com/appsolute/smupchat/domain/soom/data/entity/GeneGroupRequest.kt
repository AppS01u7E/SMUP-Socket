package com.appsolute.smupchat.domain.soom.data.entity

import com.appsolute.smupchat.domain.soom.data.type.GroupType
import com.appsolute.smupchat.global.school.type.SchoolType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime



@RedisHash(timeToLive = 51840000)
class GeneGroupRequest(
    var name: String,
    var memberId: String,
    var groupType: GroupType,
    val schoolType: SchoolType,
    var description: String
) {
    @Id
    val id = memberId+name
    val requestAt = LocalDateTime.now()
    @Indexed
    val type = groupType
    @Indexed
    val school = schoolType
    var des: String = description


}