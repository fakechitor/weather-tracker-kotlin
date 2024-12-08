package com.weathertracker.root.config.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean
    fun dataSource(): DataSource =
        HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/weather_tracker"
                username = System.getenv("DB_USER") ?: "postgres"
                password = System.getenv("DB_PASSWORD") ?: "postgres"
                driverClassName = "org.postgresql.Driver"
                maximumPoolSize = 10
            },
        )
}
