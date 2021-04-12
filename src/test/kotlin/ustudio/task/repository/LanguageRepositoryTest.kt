package ustudio.task.repository

import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.FileSystemResourceAccessor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

class LanguageRepositoryTest {
    companion object {
        private const val CORRECT_LANGUAGE = "en"
        private const val INCORRECT_LANGUAGE = "jp"

        private val datasource = getDataSource()
        private val jdbcTemplate = NamedParameterJdbcTemplate(datasource)
        private val languageRepository: LanguageRepository = LanguageRepositoryImpl(jdbcTemplate)
    }

    @BeforeEach
    fun init() {
        val db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(datasource.connection))
        val liquibase = Liquibase(
            "src/main/resources/db/changelog/migration-liquibase.xml",
            FileSystemResourceAccessor(),
            db
        )
        liquibase.update(Contexts("test"))
    }

    @Test
    fun check_for_exist_language_True() {
        Assertions.assertTrue(languageRepository.existsLanguage(CORRECT_LANGUAGE))
    }

    @Test
    fun check_for_exist_language_False() {
        Assertions.assertFalse(languageRepository.existsLanguage(INCORRECT_LANGUAGE))
    }
}
