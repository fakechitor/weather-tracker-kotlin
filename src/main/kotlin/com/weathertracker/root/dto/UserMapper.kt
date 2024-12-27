package com.weathertracker.root.dto

import com.weathertracker.root.model.User
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {
    fun convertToDto(user: User): UserDto

    fun convertToModel(dto: UserDto): User
}
