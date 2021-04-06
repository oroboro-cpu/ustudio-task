package ustudio.task.repository

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class LanguageRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate) : LanguageRepository {
    companion object {
        private const val EXIST_LANGUAGE_QUERY =
            "SELECT EXISTS(SELECT * FROM languages AS l WHERE l.language = :language)"
    }

    override fun existsLanguage(language: String): Boolean {
        return jdbcTemplate.queryForObject(EXIST_LANGUAGE_QUERY, mapOf("language" to language), Boolean::class.java)!!
    }
}
