package ustudio.task.service

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import ustudio.task.exception.InvalidCountryCodeException
import ustudio.task.exception.InvalidLanguageCodeException
import ustudio.task.exception.LocalizationNotFoundException
import ustudio.task.model.CountryLocalization
import ustudio.task.repository.CountryRepository
import ustudio.task.repository.LanguageRepository

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CountryLocalizationServiceTest {
    companion object {
        private const val VALID_ISO_CODE = "UA"
        private const val INVALID_ISO_CODE = "AA"
        private const val VALID_LANGUAGE = "en"
        private const val INVALID_LANGUAGE = "jp"
        private const val COUNTRY_NAME = "Ukraine"

        private val languageRepository: LanguageRepository = mock()
        private val countryRepository: CountryRepository = mock()
        private val countryLocalizationService: CountryLocalizationService =
            CountryLocalizationServiceImpl(countryRepository, languageRepository)
    }

    @BeforeEach
    fun init() {
        reset(languageRepository)
        reset(countryRepository)
    }

    @Test
    fun check_for_correct_language_output_and_iso_code() {
        whenever(countryRepository.getCountryByIsoCodeAndLanguage(VALID_ISO_CODE, VALID_LANGUAGE))
            .thenReturn(CountryLocalization(name = COUNTRY_NAME, iso_code = VALID_ISO_CODE))
        whenever(countryRepository.existsIsoCode(VALID_ISO_CODE)).thenReturn(true)
        whenever(languageRepository.existsLanguage(VALID_LANGUAGE)).thenReturn(true)

        val result = countryLocalizationService
            .getCountryByIsoCodeAndLanguage(VALID_ISO_CODE, VALID_LANGUAGE).name

        assertEquals(COUNTRY_NAME, result)
    }

    @Test
    fun check_for_incorrect_iso_code() {
        whenever(countryRepository.existsIsoCode(eq(INVALID_ISO_CODE)))
            .thenReturn(false)

        assertThrows(InvalidCountryCodeException::class.java) {
            countryLocalizationService.getCountryByIsoCodeAndLanguage(INVALID_ISO_CODE, VALID_LANGUAGE)
        }
    }

    @Test
    fun check_for_incorrect_data_language() {
        whenever(languageRepository.existsLanguage(eq(INVALID_LANGUAGE)))
            .thenReturn(false)
        whenever(countryRepository.existsIsoCode(VALID_ISO_CODE)).thenReturn(true)

        assertThrows(InvalidLanguageCodeException::class.java) {
            countryLocalizationService.getCountryByIsoCodeAndLanguage(VALID_ISO_CODE, INVALID_LANGUAGE)
        }
    }

    @Test
    fun check_for_localization_not_found() {
        whenever(countryRepository.getCountryByIsoCodeAndLanguage(VALID_ISO_CODE, VALID_LANGUAGE))
            .thenReturn(null)
        whenever(countryRepository.existsIsoCode(VALID_ISO_CODE)).thenReturn(true)
        whenever(languageRepository.existsLanguage(VALID_LANGUAGE)).thenReturn(true)

        assertThrows(LocalizationNotFoundException::class.java) {
            countryLocalizationService.getCountryByIsoCodeAndLanguage(VALID_ISO_CODE, VALID_LANGUAGE)
        }
    }
}
