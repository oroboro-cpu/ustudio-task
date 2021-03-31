package ustudio.task.service

import org.springframework.stereotype.Service
import ustudio.task.exception.InvalidCountryCodeException
import ustudio.task.exception.InvalidLanguageCodeException
import ustudio.task.exception.LocalizationNotFoundException
import ustudio.task.model.CountryLocalization
import ustudio.task.repository.CountryLocalizationRepository
import ustudio.task.repository.LanguageRepository

@Service
class CountryLocalizationServiceImpl(
    val countryRepository: CountryLocalizationRepository,
    val languageRepository: LanguageRepository
) :
    CountryLocalizationService {
    override fun getCountryByIsoCodeAndLanguage(isoCode: String, language: String): CountryLocalization {
        if (!countryRepository.existsIsoCode(isoCode)) throw InvalidCountryCodeException()
        if (!languageRepository.existsLanguage(language)) throw InvalidLanguageCodeException()
        val country = countryRepository.getCountryByIsoCodeAndLanguage(isoCode, language)
        return country ?: throw LocalizationNotFoundException()
    }
}
