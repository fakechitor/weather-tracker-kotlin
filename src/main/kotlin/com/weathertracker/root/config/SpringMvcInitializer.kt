package com.weathertracker.root.config

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class SpringMvcInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getRootConfigClasses(): Array<out Class<*>?>? = null

    override fun getServletConfigClasses(): Array<out Class<*>?>? = arrayOf(SpringConfiguration::class.java)

    override fun getServletMappings(): Array<out String?> = arrayOf("/")
}
