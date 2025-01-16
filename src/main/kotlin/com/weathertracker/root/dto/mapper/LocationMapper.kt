package com.weathertracker.root.dto.mapper

import com.weathertracker.root.dto.LocationDto
import com.weathertracker.root.model.Location
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LocationMapper {
    fun convertToDto(location: Location): LocationDto

    @Mapping(target = "user", ignore = true)
    fun convertToModel(dto: LocationDto): Location
}
