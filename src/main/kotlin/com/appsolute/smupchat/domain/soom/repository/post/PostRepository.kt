package com.appsolute.smupchat.domain.soom.repository.post

import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.soom.data.entity.Soom
import com.appsolute.smupchat.domain.soom.data.entity.Notice
import com.appsolute.smupchat.domain.soom.data.entity.Post
import com.appsolute.smupchat.domain.soom.data.type.PostType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PostRepository: JpaRepository<Post, String> {

    fun findAllByGroupAndPostType(soom: Soom, type: PostType, pageable: Pageable): Page<Post>
    fun findByGroupAndIdAndPostType(soom: Soom, id: String, type: PostType): Optional<Post>
    fun findAllByReceiverListContainsAndPostType(user: User, pageable: Pageable, postType: PostType): Page<Notice>

}