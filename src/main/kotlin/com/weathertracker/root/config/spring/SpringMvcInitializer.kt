package com.weathertracker.root.config.spring

import com.weathertracker.root.config.database.DataSourceConfig
import com.weathertracker.root.config.database.FlywayConfig
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class SpringMvcInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getRootConfigClasses(): Array<Class<*>>? = arrayOf(DataSourceConfig::class.java, FlywayConfig::class.java)

    override fun getServletConfigClasses(): Array<out Class<*>?>? = arrayOf(SpringConfiguration::class.java)

    override fun getServletMappings(): Array<out String?> = arrayOf("/")
}
