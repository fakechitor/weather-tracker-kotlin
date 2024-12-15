package com.weathertracker.root.config.database

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import javax.sql.DataSource

@PropertySource("classpath:application-dev.properties")
@Configuration
class DataSourceConfig(
    private val environment: Environment,
) {
    @Bean
    fun dataSource(): DataSource =
        HikariDataSource().apply {
            this.jdbcUrl = environment.getProperty("datasource.url")
            this.username = environment.getProperty("datasource.username")
            this.password = environment.getProperty("datasource.password")
            this.driverClassName = environment.getProperty("datasource.driver-class-name")
            this.maximumPoolSize = 10
        }
}
