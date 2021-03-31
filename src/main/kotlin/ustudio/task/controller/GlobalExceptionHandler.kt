package ustudio.task.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ustudio.task.exception.InvalidCountryCodeException
import ustudio.task.exception.InvalidLanguageCodeException
import ustudio.task.exception.LocalizationNotFoundException


@RestControllerAdvice
class ExceptionsHandler {
    class JsonResponse(val message: String)

    @ExceptionHandler(value = [InvalidCountryCodeException::class])
    fun handleIsoCodeNotFoundException(e: InvalidCountryCodeException): ResponseEntity<JsonResponse> {
        return ResponseEntity(JsonResponse(e.message!!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [InvalidLanguageCodeException::class])
    fun handleLanguageNotFoundException(e: InvalidLanguageCodeException): ResponseEntity<JsonResponse> {
        return ResponseEntity(JsonResponse(e.message!!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [LocalizationNotFoundException::class])
    fun handleLocalizationNotFoundException(e: LocalizationNotFoundException): ResponseEntity<JsonResponse> {
        return ResponseEntity(JsonResponse(e.message!!), HttpStatus.NOT_FOUND)
    }
}