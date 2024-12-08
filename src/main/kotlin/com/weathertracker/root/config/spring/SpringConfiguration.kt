package com.weathertracker.root.config.spring

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring6.view.ThymeleafViewResolver

@Configuration
@ComponentScan("com.weathertracker.root")
@EnableWebMvc
class SpringConfiguration(
    val applicationContext: ApplicationContext,
) : WebMvcConfigurer {
    @Bean
    fun templateResolver(): SpringResourceTemplateResolver =
        SpringResourceTemplateResolver().apply {
            setApplicationContext(applicationContext)
            prefix = "/WEB-INF/views/"
            suffix = ".html"
            isCacheable = false
        }

    @Bean
    fun templateEngine(): SpringTemplateEngine =
        SpringTemplateEngine().apply {
            setTemplateResolver(templateResolver())
            enableSpringELCompiler = true
        }

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        registry.viewResolver(ThymeleafViewResolver().apply { templateEngine = templateEngine() })
    }
}
