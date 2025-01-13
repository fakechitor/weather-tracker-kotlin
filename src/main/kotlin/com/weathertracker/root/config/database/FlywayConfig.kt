package com.weathertracker.root.config.database

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfig(
    private val dataSource: DataSourceConfig,
) {
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway =
        Flyway
            .configure()
            .dataSource(dataSource.dataSource())
            .locations("classpath:db.migration")
            .schemas("public")
            .baselineOnMigrate(true)
            .baselineVersion("0")
            .load()
}
