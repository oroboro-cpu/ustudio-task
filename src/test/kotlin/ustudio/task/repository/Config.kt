package ustudio.task.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

fun getDataSource(): DataSource = HikariDataSource(hikariConfig())

fun hikariConfig(): HikariConfig {
    postgreSQLContainer.start()
    return HikariConfig().apply {
        jdbcUrl = postgreSQLContainer.jdbcUrl
        username = postgreSQLContainer.username
        password = postgreSQLContainer.password
    }
}

@Container
private val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:9.6-alpine").apply {
    withDatabaseName("countries")
    withUsername("yaroslav")
    withPassword("postgres")
}
