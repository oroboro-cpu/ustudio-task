package ustudio.task

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CountryLocalizationApplication

fun main(args: Array<String>) {
    runApplication<CountryLocalizationApplication>(*args)
}
