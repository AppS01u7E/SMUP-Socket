package com.appsolute.smupchat.domain.account.data.entity.user

import com.appsolute.smupchat.domain.chat.data.entity.ChatRoom
import com.appsolute.smupchat.domain.chat.service.ChatService
import com.appsolute.smupchat.global.school.type.SchoolType
import com.appsolute.smupchat.domain.account.data.dto.response.UserResponse
import com.appsolute.smupchat.domain.account.data.dto.inner.MinimUserDto
import com.appsolute.smupchat.domain.account.data.entity.user.type.Gender
import com.appsolute.smupchat.domain.account.data.entity.user.type.Role
import com.appsolute.smupchat.domain.soom.data.entity.Soom
import com.appsolute.smupchat.domain.soom.data.entity.Post
import com.appsolute.smupchat.domain.soom.data.type.GroupAuthType
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient


@Entity
@DiscriminatorColumn(name = "UTYPE")
abstract class User (
    id: String,
    email: String,
    firstName: String,
    lastName: String,
    gender: Gender,
    birth: String,
    password: String,
    role: Role,
    school: SchoolType
        ){
    @Id
    val id: String = id

    private val email: String = email

    private var firstName = firstName

    private var lastName = lastName

    private var gender = gender

    private var birth = birth

    var password = password

    private var role = role

    var profile: String? = null

    var school: SchoolType = school
    @OneToMany(fetch = FetchType.LAZY)
    var groupInfo: MutableList<GroupInfo> = ArrayList<GroupInfo> ()

    var createdAt = LocalDateTime.now()

    @OneToMany(fetch = FetchType.LAZY)
    var post: MutableList<Post> = ArrayList<Post>()

    @ManyToMany(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
    private val chatRoomList: MutableList<ChatRoom> = ArrayList<ChatRoom>()


    @Transient
    private lateinit var chatService: ChatService
    fun getEmail(): String{
        return this.email
    }

    fun getChatRoomList(): List<ChatRoom>{
        return this.chatRoomList
    }


    fun getRole(): Role {
        return this.role
    }

    fun getFirstName(): String{
        return this.firstName
    }

    fun getLastName(): String{
        return this.lastName
    }

    fun getGender(): Gender {
        return this.gender
    }

    fun joinGroup(soom: Soom): User {
        this.groupInfo.add(GroupInfo(this, soom, GroupAuthType.MEMBER))
        return this
    }


    fun settingProfile(profile: String){
        this.profile = profile
    }

    fun toUserResponse(): UserResponse {
        return UserResponse(
            this.id,
            this.email,
            this.firstName,
            this.lastName,
            this.gender,
            this.role
        )
    }

    fun toMinimUserDto(): MinimUserDto{
        return MinimUserDto(
            this.id,
            this.email,
            this.firstName,
            this.lastName,
            this.gender,
            this.birth,
            this.school,
            this.role
        )
    }



}