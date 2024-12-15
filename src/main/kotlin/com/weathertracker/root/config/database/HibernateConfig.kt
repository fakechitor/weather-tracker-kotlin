package com.weathertracker.root.config.database

import com.weathertracker.root.model.Location
import com.weathertracker.root.model.Session
import com.weathertracker.root.model.User
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

@Configuration
@PropertySource("classpath:application-dev.properties")
class HibernateConfig(
    private val environment: Environment,
) {
    @Bean
    fun sessionFactory(dataSource: DataSourceConfig): SessionFactory {
        val serviceRegistry =
            StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", environment.getProperty("datasource.driver-class-name"))
                .applySetting("hibernate.connection.url", environment.getProperty("datasource.url"))
                .applySetting("hibernate.connection.username", environment.getProperty("datasource.username"))
                .applySetting("hibernate.connection.password", environment.getProperty("datasource.password"))
                .applySetting("hibernate.dialect", environment.getProperty("jpa.database-platform"))
                .applySetting("hibernate.hbm2ddl.auto", environment.getProperty("jpa.hibernate.ddl-auto"))
                .build()

        return MetadataSources(serviceRegistry)
            .addAnnotatedClass(User::class.java)
            .addAnnotatedClass(Session::class.java)
            .addAnnotatedClass(Location::class.java)
            .buildMetadata()
            .buildSessionFactory()
    }
}
