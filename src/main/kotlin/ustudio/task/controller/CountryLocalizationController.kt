package ustudio.task.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ustudio.task.model.dto.CountryResponse
import ustudio.task.service.CountryLocalizationService
import ustudio.task.service.mapper.toResponse

@RestController
@RequestMapping("/countries")
class CountryLocalizationController @Autowired constructor(
    val countryService: CountryLocalizationService
) {

    @GetMapping("/{isoCode}")
    fun getCountryByIsoCode(@PathVariable isoCode: String, @RequestParam lang: String): CountryResponse {
        val country = countryService.getCountryByIsoCodeAndLanguage(isoCode, lang)
        return country.toResponse()
    }
}
