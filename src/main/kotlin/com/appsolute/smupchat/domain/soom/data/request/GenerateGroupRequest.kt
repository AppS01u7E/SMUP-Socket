package com.appsolute.smupchat.domain.soom.data.request

import com.appsolute.smupchat.domain.soom.data.type.GroupType

data class GenerateGroupRequest (
    var name: String,
    var description: String,
    var type: GroupType
)