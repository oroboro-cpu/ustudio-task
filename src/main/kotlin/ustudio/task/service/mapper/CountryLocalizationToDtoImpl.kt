package ustudio.task.service.mapper

import org.springframework.stereotype.Component
import ustudio.task.model.CountryLocalization
import ustudio.task.model.dto.CountryLocalizationDto

@Component
class CountryLocalizationToDtoImpl : CountryLocalizationToDto {
    override fun toDto(entity: CountryLocalization): CountryLocalizationDto {
        return CountryLocalizationDto(entity.name, entity.iso_code)
    }
}
