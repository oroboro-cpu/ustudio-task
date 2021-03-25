package ustudio.task.service

import ustudio.task.model.CountryLocalization
import java.util.*

interface CountryLocalizationService {
    fun getCountryByIsoCode(isoCode: String, language: String): CountryLocalization
}
