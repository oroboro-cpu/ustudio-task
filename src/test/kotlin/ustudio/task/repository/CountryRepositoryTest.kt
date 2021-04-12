package ustudio.task.repository

import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.FileSystemResourceAccessor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CountryRepositoryTest {
    companion object {
        private const val CORRECT_ISO_CODE = "UA"
        private const val INCORRECT_ISO_CODE = "QQ"
        private const val CORRECT_LANGUAGE = "en"
        private const val INCORRECT_LANGUAGE = "jp"
        private const val COUNTRY_NAME = "Ukraine"

        private val datasource = getDataSource()
        private val jdbcTemplate = NamedParameterJdbcTemplate(datasource)
        private val countryRepository: CountryRepository = CountryRepositoryImpl(jdbcTemplate)
    }

    @BeforeEach
    fun init() {
        val db = DatabaseFactory.getInstance()
            .findCorrectDatabaseImplementation(JdbcConnection(datasource.connection))
        val liquibase = Liquibase(
            "src/main/resources/db/changelog/migration-liquibase.xml",
            FileSystemResourceAccessor(), db
        )
        liquibase.update(Contexts())
    }

    @Test
    internal fun check_for_correct_data() {
        val country = countryRepository.getCountryByIsoCodeAndLanguage(CORRECT_ISO_CODE, CORRECT_LANGUAGE)
        assertNotNull(country)
        assertEquals(COUNTRY_NAME, country.name)
    }

    @Test
    internal fun check_for_incorrect_iso_code() {
        val country =
            countryRepository.getCountryByIsoCodeAndLanguage(INCORRECT_ISO_CODE, CORRECT_LANGUAGE)
        assertNull(country)
    }

    @Test
    internal fun check_for_incorrect_language() {
        val country = countryRepository.getCountryByIsoCodeAndLanguage(CORRECT_ISO_CODE, INCORRECT_LANGUAGE)
        assertNull(country)
    }

    @Test
    internal fun check_for_exist_iso_code_True() {
        assertTrue(countryRepository.existsIsoCode(CORRECT_ISO_CODE))
    }

    @Test
    internal fun check_for_exist_iso_code_False() {
        assertFalse(countryRepository.existsIsoCode(INCORRECT_ISO_CODE))
    }
}
