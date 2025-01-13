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
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@PropertySource("classpath:application.properties")
class HibernateConfig(
    private val environment: Environment,
) {
    @Bean
    fun sessionFactory(dataSource: DataSource): SessionFactory {
        val serviceRegistry =
            StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.datasource", dataSource)
                .applySetting("hibernate.dialect", environment.getProperty("jpa.database-platform"))
                .applySetting("hibernate.hbm2ddl.auto", environment.getProperty("jpa.hibernate.ddl-auto"))
                .applySetting("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext")
                .build()

        return MetadataSources(serviceRegistry)
            .addAnnotatedClass(User::class.java)
            .addAnnotatedClass(Session::class.java)
            .addAnnotatedClass(Location::class.java)
            .buildMetadata()
            .buildSessionFactory()
    }

    @Bean
    fun transactionManager(sessionFactory: SessionFactory): PlatformTransactionManager = HibernateTransactionManager(sessionFactory)
}
