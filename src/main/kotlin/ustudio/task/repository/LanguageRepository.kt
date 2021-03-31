package ustudio.task.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ustudio.task.model.CountryLocalization

@Repository
interface LanguageRepository : JpaRepository<CountryLocalization, Long> {
    @Query(
        value = "SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
                "FROM languages AS l WHERE l.language = :language ",
        nativeQuery = true
    )
    fun existsLanguage(@Param("language") language: String): Boolean
}
