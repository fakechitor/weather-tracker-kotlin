package com.weathertracker.root.config.database

import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.core.env.getProperty
import java.sql.Connection
import javax.sql.DataSource

@Profile("dev")
@PropertySource("classpath:application-dev.properties")
@Configuration
class DataSourceConfig(
    private val environment: Environment,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun dataSource(): DataSource =
        HikariDataSource().apply {
            this.jdbcUrl = environment.getProperty("spring.datasource.url")
            this.username = environment.getProperty("spring.datasource.username")
            this.password = environment.getProperty("spring.datasource.password")
            this.driverClassName = environment.getProperty("spring.datasource.driver-class-name")
            this.maximumPoolSize = 10
        }

    fun getConnection(): Connection = dataSource().connection
}
