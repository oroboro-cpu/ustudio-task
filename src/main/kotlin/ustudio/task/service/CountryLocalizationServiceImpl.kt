package ustudio.task.service

import org.springframework.stereotype.Service
import ustudio.task.exception.DataProcessingException
import ustudio.task.model.CountryLocalization
import ustudio.task.repository.CountryLocalizationRepository

import java.util.*

@Service
class CountryLocalizationServiceImpl (val countryRepository: CountryLocalizationRepository) :
    CountryLocalizationService {
    override fun getCountryByIsoCode(isoCode: String, language: String): CountryLocalization {
        return countryRepository.getCountryByIsoCode(isoCode, language)
    }
}