package com.appsolute.smupchat.domain.soom.repository.group

import com.appsolute.smupchat.domain.soom.data.entity.GeneGroupRequest
import com.appsolute.smupchat.domain.soom.data.type.GroupType
import com.appsolute.smupchat.global.school.type.SchoolType
import org.springframework.data.repository.CrudRepository

interface GeneGroupRequestRepository: CrudRepository<GeneGroupRequest, String> {

    fun findAllBySchool(school: SchoolType): List<GeneGroupRequest>
    fun findAllBySchoolAndGroupType(school: SchoolType, groupType: GroupType): List<GeneGroupRequest>

}