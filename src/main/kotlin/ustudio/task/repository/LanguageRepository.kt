package ustudio.task.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ustudio.task.model.CountryLocalization

@Repository
interface LanguageRepository : JpaRepository<CountryLocalization, Long> {
    @Query(
        value = "SELECT EXISTS(SELECT l.language FROM languages AS l WHERE l.language = :language)",
        nativeQuery = true
    )
    fun existsLanguage(@Param("language") language: String): Boolean
}
