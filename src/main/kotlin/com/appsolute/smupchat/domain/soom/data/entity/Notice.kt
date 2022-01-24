package com.appsolute.smupchat.domain.soom.data.entity

import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.soom.data.type.PostType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity


@Entity
@DiscriminatorValue("NOTICE")
class Notice(
    id: String,
    title: String,
    content: String,
    writer: User,
    soom: Soom
):  Post(
    id,
    title,
    PostType.NOTICE,
    writer,
    null,
    soom
) {

    private var content: String = content


}