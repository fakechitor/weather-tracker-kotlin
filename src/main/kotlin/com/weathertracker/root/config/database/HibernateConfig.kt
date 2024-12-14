package com.weathertracker.root.config.database

import com.weathertracker.root.model.Location
import com.weathertracker.root.model.Session
import com.weathertracker.root.model.User
import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.springframework.context.annotation.Bean

class HibernateConfig {
    @Bean
    fun sessionFactory(dataSource: DataSourceConfig): SessionFactory {
        val serviceRegistry =
            StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", System.getProperty("hibernate.connection.driver_class"))
                .applySetting("hibernate.connection.url", System.getProperty("spring.datasource.url"))
                .applySetting("hibernate.connection.username", System.getProperty("spring.datasource.username"))
                .applySetting("hibernate.connection.password", System.getProperty("spring.datasource.password"))
                .applySetting("hibernate.dialect", System.getProperty("spring.jpa.database-platform"))
                .applySetting("hibernate.hbm2ddl.auto", System.getProperty("spring.jpa.hibernate.ddl-auto"))
                .build()

        return MetadataSources(serviceRegistry)
            .addAnnotatedClass(User::class.java)
            .addAnnotatedClass(Session::class.java)
            .addAnnotatedClass(Location::class.java)
            .buildMetadata()
            .buildSessionFactory()
    }
}
