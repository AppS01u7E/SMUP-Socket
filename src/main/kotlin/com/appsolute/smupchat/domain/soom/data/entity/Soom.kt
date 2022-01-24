package com.appsolute.smupchat.domain.soom.data.entity

import com.appsolute.smupchat.domain.chat.data.entity.ChatRoom
import com.appsolute.smupchat.domain.chat.data.type.ChatRoomType
import com.appsolute.smupchat.domain.account.data.entity.user.GroupInfo
import com.appsolute.smupchat.domain.account.data.entity.user.Student
import com.appsolute.smupchat.domain.account.data.entity.user.Teacher
import com.appsolute.smupchat.domain.account.data.entity.user.User
import com.appsolute.smupchat.domain.soom.data.request.EditGroupRequest
import com.appsolute.smupchat.domain.soom.data.response.GroupResponse
import com.appsolute.smupchat.domain.soom.data.type.GroupAuthPolicyType
import com.appsolute.smupchat.domain.soom.data.type.GroupType
import com.appsolute.smupchat.domain.soom.repository.group.GroupInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.*
import kotlin.collections.ArrayList
import kotlin.jvm.Transient


@Entity
class Soom(
    id: String,
    name: String,
    description: String,
    type: GroupType,
    header: User
) {
    @Id
    var id: String = id
    var name = name
    var description = description
    var type: GroupType = type
    @ManyToOne(fetch = FetchType.LAZY)
    var header: User = header
    @OneToMany(fetch = FetchType.LAZY)
    var subHeaderList: MutableList<User> = ArrayList<User>()
    @ManyToMany(fetch = FetchType.LAZY)
    var memberList: MutableList<User> = ArrayList<User>()
    var profile: String? = null

    @ElementCollection
    var subHeaderAuthPolicy: MutableList<GroupAuthPolicyType> = ArrayList<GroupAuthPolicyType>()
    @ManyToOne(fetch = FetchType.LAZY)
    var teacher: Teacher? = null

    @OneToMany(fetch = FetchType.LAZY)
    var deleteVoterList: MutableList<User> = ArrayList<User>()

    @OneToOne(fetch = FetchType.LAZY)
    var chattingRoom: ChatRoom = ChatRoom(this.id, name, ChatRoomType.GROUP, this.profile, this.memberList, this.header, this)

    @OneToMany(fetch = FetchType.LAZY)
    var joinRequestMemberList: MutableList<Student> = ArrayList<Student>()

    @OneToMany(fetch = FetchType.LAZY)
    var postList: MutableList<Post> = ArrayList<Post>()


    @Autowired
    @Transient
    private lateinit var groupInfoRepository: GroupInfoRepository



    fun setSubHeader(member: User): Soom{
        this.subHeaderList.add(member)
        return this
    }

    fun dismissalSubHeader(member: User): Soom{
        this.subHeaderList.remove(member)
        return this
    }

    fun addJoinRequestMemberList(student: Student){
        this.joinRequestMemberList.add(student)
    }

    fun cancelJoinRequest(student: Student){
        this.joinRequestMemberList.remove(student)

    }

    fun approveJoinRequest(student: Student){
        this.joinRequestMemberList.remove(student)
        this.memberList.add(student)
        student.joinGroup(this)
    }

    fun addMemberList(user: User){
        this.memberList.add(user)
    }

    fun removeMember(user: User){
        this.memberList.remove(user)
    }

    fun settingProfile(imageUrl: String){
        this.profile = imageUrl
    }

    fun settingTeacher(teacher: Teacher){
        this.teacher = teacher
    }

    fun toGroupResponse(): GroupResponse{
        return GroupResponse(
            this.id,
            this.name,
            this.description,
            this.type,
            this.header,
            this.subHeaderList.stream().map { it.toUserResponse() }.toList(),
            this.profile,
            this.memberList.size,
            this.memberList.stream().map { it.toUserResponse() }.toList(),
            this.teacher?.toTeacherResponse()
        )

    }

    fun editGroup(r: EditGroupRequest): Soom{
        this.name = r.name
        this.description = r.description
        return this
    }

    fun setType(type: GroupType): Soom{
        this.type = type
        return this
    }

    fun deleteVote(member: User): Soom{
        if (deleteVoterList.contains(member)) {
            this.deleteVoterList.remove(member)
        } else{
            this.deleteVoterList.add(member)
        }

        return this
    }

    fun kickMember(member: User, groupInfo: GroupInfo): Soom{
        this.memberList.remove(member)
        groupInfoRepository.delete(groupInfo)
        return this
    }


}