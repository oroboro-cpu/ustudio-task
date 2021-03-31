package ustudio.task.service

import ustudio.task.model.CountryLocalization

interface CountryLocalizationService {
    fun getCountryByIsoCodeAndLanguage(isoCode: String, language: String): CountryLocalization
}
