package ustudio.task.repository

interface LanguageRepository {
    fun existsLanguage(language: String): Boolean
}
