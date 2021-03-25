package ustudio.task.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ustudio.task.exception.InvalidCountryCodeException
import ustudio.task.exception.InvalidLanguageCodeException
import ustudio.task.exception.LocalizationNotFoundException
import ustudio.task.model.CountryLocalization
import ustudio.task.repository.CountryLocalizationRepository

internal class CountryLocalizationServiceTest {
    val repository: CountryLocalizationRepository = mock()
    val countryLocalizationService: CountryLocalizationService = CountryLocalizationServiceImpl(repository)

    @Test
    fun check_for_correct_language_output_and_iso_code() {
        whenever(repository.getCountryByIsoCode("UA", "en"))
            .thenReturn(CountryLocalization().apply { name = "Ukraine" })
        val expectedResult1 = "Ukraine"
        val actualResult1 = countryLocalizationService.getCountryByIsoCode("UA", "en").name
        assertEquals(expectedResult1, actualResult1)
    }

    @Test
    fun check_for_incorrect_iso_code() {
        whenever(repository.getCountryByIsoCode("UA", "en"))
            .thenReturn(CountryLocalization().apply { name = "Ukraine" })
        assertThrows(InvalidCountryCodeException::class.java) {
            countryLocalizationService.getCountryByIsoCode("AA", "en")
        }
    }
    @Test
    fun check_for_incorrect_data_language() {
        whenever(repository.getCountryByIsoCode("UA", "en"))
            .thenReturn(CountryLocalization().apply { name = "Ukraine" })
        assertThrows(InvalidLanguageCodeException::class.java) {
            countryLocalizationService.getCountryByIsoCode("RU", "jp")
        }
    }

    @Test
    fun check_for_localization_not_found() {
        whenever(repository.getCountryByIsoCode("UA", "en"))
            .thenReturn(CountryLocalization().apply { name = "Ukraine" })
        assertThrows(LocalizationNotFoundException::class.java) {
            countryLocalizationService.getCountryByIsoCode("UA", "uk")
        }
    }
}
