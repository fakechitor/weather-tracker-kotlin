package com.weathertracker.root.dto

import com.weathertracker.root.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {
    fun convertToDto(user: User): LoginUserDto

    @Mapping(source = "username", target = "login")
    fun convertToModel(dto: LoginUserDto): User
}
