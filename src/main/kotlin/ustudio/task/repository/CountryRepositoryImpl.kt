package ustudio.task.repository

import org.intellij.lang.annotations.Language
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ustudio.task.model.CountryLocalization
import java.sql.ResultSet

@Repository
class CountryRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate) : CountryRepository {
    companion object {
        @Language("PostgreSQL")
        private const val EXIST_ISO_CODE_QUERY = """SELECT EXISTS(SELECT * 
                FROM country_information AS ci WHERE ci.iso_code = :isoCode)"""
        @Language("PostgreSQL")
        private const val GET_COUNTRY_QUERY ="""SELECT c.name, c.id, ci.iso_code FROM country AS c
                INNER JOIN languages AS l ON l.id = c.language_id
                INNER JOIN country_information AS ci ON ci.id = c.iso_code_id
                WHERE ci.iso_code = :isoCode AND l.language = :language"""
    }

    override fun existsIsoCode(isoCode: String): Boolean {
        return jdbcTemplate.queryForObject(EXIST_ISO_CODE_QUERY, mapOf("isoCode" to isoCode), Boolean::class.java)!!
    }


    override fun getCountryByIsoCodeAndLanguage(isoCode: String, language: String): CountryLocalization? =
        jdbcTemplate.query(GET_COUNTRY_QUERY, mapOf("isoCode" to isoCode, "language" to language), countryLocalizationMapper)

    private val countryLocalizationMapper = { rs: ResultSet ->
        if (rs.next()) {
            CountryLocalization(
                rs.getString("name"),
                rs.getString("iso_code")
            )
        } else null
    }
}
