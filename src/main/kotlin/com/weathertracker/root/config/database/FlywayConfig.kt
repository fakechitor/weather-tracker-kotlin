package com.weathertracker.root.config.database

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

class FlywayConfig(
    private val dataSource: DataSource,
) {
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway =
        Flyway
            .configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .schemas("public")
            .load()
}
