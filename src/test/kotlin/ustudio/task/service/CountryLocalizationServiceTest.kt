package ustudio.task.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ustudio.task.exception.DataProcessingException

@SpringBootTest
internal class CountryLocalizationServiceTest(
    @Autowired
    val countryLocalizationService: CountryLocalizationService
) {

    @Test
    fun check_for_correct_language_output() {
        val expectedResult1 = "Ukraine"
        val expectedResult2 = "Украина"
        val expectedResult3 = "Украiна"
        val actualResult1 = countryLocalizationService.getCountryByIsoCode("UA", "en").name
        val actualResult2 = countryLocalizationService.getCountryByIsoCode("UA", "ru").name
        val actualResult3 = countryLocalizationService.getCountryByIsoCode("UA", "uk").name
        assertEquals(expectedResult1, actualResult1)
        assertEquals(expectedResult2, actualResult2)
        assertEquals(expectedResult3, actualResult3)
    }

    @Test
    fun check_for_correct_iso_code() {
        val expectedResult1 = "Россия"
        val expectedResult2 = "Russia"
        val actualResult1 = countryLocalizationService.getCountryByIsoCode("RU", "ru").name
        val actualResult2 = countryLocalizationService.getCountryByIsoCode("RU", "en").name
        assertEquals(expectedResult1, actualResult1)
        assertEquals(expectedResult2, actualResult2)
    }

    @Test
    fun check_for_incorrect_data() {
        assertThrows(Exception::class.java) {
            countryLocalizationService.getCountryByIsoCode("RU", "jp")
        }
        assertThrows(Exception::class.java) {
            countryLocalizationService.getCountryByIsoCode("UA", "french")
        }
        assertThrows(Exception::class.java) {
            countryLocalizationService.getCountryByIsoCode("AA", "en")
        }
        assertThrows(Exception::class.java) {
            countryLocalizationService.getCountryByIsoCode("UU", "ru")
        }
    }
}