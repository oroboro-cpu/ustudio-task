package ustudio.task.service

import org.springframework.stereotype.Service
import ustudio.task.exception.InvalidCountryCodeException
import ustudio.task.exception.InvalidLanguageCodeException
import ustudio.task.exception.LocalizationNotFoundException
import ustudio.task.model.CountryLocalization
import ustudio.task.repository.CountryLocalizationRepository

@Service
class CountryLocalizationServiceImpl (val countryRepository: CountryLocalizationRepository) :
    CountryLocalizationService {
    override fun getCountryByIsoCode(isoCode: String, language: String): CountryLocalization {
        val repository = countryRepository.getCountryByIsoCode(isoCode, language)
        if (isoCode != "RU" && isoCode!= "UA") return repository ?: throw InvalidCountryCodeException()
        if (language != "en" && language != "uk" && language != "ru")
            return repository ?: throw InvalidLanguageCodeException()
        return repository ?: throw LocalizationNotFoundException()
    }
}
