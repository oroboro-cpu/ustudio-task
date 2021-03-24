package ustudio.task.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ustudio.task.model.dto.CountryLocalizationDto
import ustudio.task.service.CountryLocalizationService
import ustudio.task.service.mapper.CountryLocalizationToDto

@RestController
@RequestMapping("/countries")
class CountryLocalizationController @Autowired constructor(
    val countryService: CountryLocalizationService,
    val countryDto: CountryLocalizationToDto
) {
    @RequestMapping("/{isoCode}")
    @GetMapping
    fun getCountryByIsoCode(@PathVariable isoCode: String, @RequestParam lang: String): CountryLocalizationDto {
        val country= countryService.getCountryByIsoCode(isoCode, lang)
        return countryDto.toDto(country)
    }
}