package ustudio.task.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ustudio.task.model.CountryLocalization
import java.util.Optional

interface CountryLocalizationRepository : JpaRepository<CountryLocalization?, Long?> {
    @Query(value = "SELECT c.name, c.id FROM country AS c" +
            " LEFT JOIN languages AS l ON l.id = c.language_id" +
            " LEFT JOIN country_information AS ci ON ci.id = c.iso_code_id" +
            " WHERE ci.iso_code = :isoCode AND l.language = :language",
        nativeQuery = true)
    fun getCountryByIsoCode(
        @Param("isoCode") isoCode: String?,
        @Param("language") language: String?
    ): CountryLocalization
}