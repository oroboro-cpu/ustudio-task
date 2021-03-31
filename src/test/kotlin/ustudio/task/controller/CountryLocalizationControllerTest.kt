package ustudio.task.controller

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ustudio.task.exception.InvalidCountryCodeException
import ustudio.task.exception.InvalidLanguageCodeException
import ustudio.task.exception.LocalizationNotFoundException
import ustudio.task.model.CountryLocalization
import ustudio.task.service.CountryLocalizationService
import ustudio.task.service.mapper.CountryLocalizationToDtoImpl


internal class CountryLocalizationControllerTest {
    companion object {
        private const val VALID_ISO_CODE = "UA"
        private const val INVALID_ISO_CODE = "AA"
        private const val VALID_LANGUAGE = "en"
        private const val INVALID_LANGUAGE = "jp"
        private const val COUNTRY_NAME_ENG = "Ukraine"
        private const val INVALID_ISO_CODE_URL = "/countries/$INVALID_ISO_CODE?lang=$VALID_LANGUAGE"
        private const val VALID_URL = "/countries/$VALID_ISO_CODE?lang=$VALID_LANGUAGE"
        private const val INVALID_LANGUAGE_URL = "/countries/$VALID_ISO_CODE?lang=$INVALID_LANGUAGE"

        private val countryLocalizationService: CountryLocalizationService = mock()
        private val convertor = CountryLocalizationToDtoImpl()
        private val handler = ExceptionsHandler()
        private val mockMvc = MockMvcBuilders.standaloneSetup(
            CountryLocalizationController(countryLocalizationService, convertor)
        ).setControllerAdvice(handler).build()
    }

    @BeforeEach
    internal fun init() {
        reset(countryLocalizationService)
    }

    @Test
    fun check_for_correct_response() {
        whenever(countryLocalizationService.getCountryByIsoCodeAndLanguage(VALID_ISO_CODE, VALID_LANGUAGE))
            .thenReturn(CountryLocalization().apply {
                name = COUNTRY_NAME_ENG
                iso_code = VALID_ISO_CODE
            })

        mockMvc.perform(get(VALID_URL))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name").value(COUNTRY_NAME_ENG))
            .andExpect(jsonPath("$.code").value(VALID_ISO_CODE))
    }

    @Test
    fun check_for_incorrect_language() {
        whenever(countryLocalizationService.getCountryByIsoCodeAndLanguage(VALID_ISO_CODE, INVALID_LANGUAGE))
            .thenThrow(InvalidLanguageCodeException())

        mockMvc.perform(get(INVALID_LANGUAGE_URL))
            .andDo(print())
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.message").value("Incorrect language"))
    }

    @Test
    fun check_for_incorrect_iso_code() {
        whenever(countryLocalizationService.getCountryByIsoCodeAndLanguage(INVALID_ISO_CODE, VALID_LANGUAGE))
            .thenThrow(InvalidCountryCodeException())

        mockMvc.perform(get(INVALID_ISO_CODE_URL))
            .andDo(print())
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.message").value("Incorrect iso code"))
    }

    @Test
    fun check_for_localization_not_found() {
        whenever(
            countryLocalizationService.getCountryByIsoCodeAndLanguage(VALID_ISO_CODE, VALID_LANGUAGE)
        )
            .thenThrow(LocalizationNotFoundException())

        mockMvc.perform(get(VALID_URL))
            .andDo(print())
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.message").value("Can't find country in DB"))
    }
}
