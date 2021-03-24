package ustudio.task.service.mapper

import ustudio.task.model.CountryLocalization
import ustudio.task.model.dto.CountryLocalizationDto

interface CountryLocalizationToDto {
    fun toDto(entity: CountryLocalization): CountryLocalizationDto
}