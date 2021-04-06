package ustudio.task.repository

import ustudio.task.model.CountryLocalization

interface CountryRepository {
    fun existsIsoCode(isoCode: String): Boolean

    fun getCountryByIsoCodeAndLanguage(isoCode: String, language: String): CountryLocalization?
}
