package com.weathertracker.root.config.spring

import com.weathertracker.root.controller.AuthInterceptor
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.*
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring6.view.ThymeleafViewResolver

@Configuration
@ComponentScan("com.weathertracker.root")
@EnableWebMvc
class SpringConfiguration(
    private val applicationContext: ApplicationContext,
    private val authInterceptor: AuthInterceptor,
) : WebMvcConfigurer {
    @Bean
    fun templateResolver(): SpringResourceTemplateResolver =
        SpringResourceTemplateResolver().apply {
            setApplicationContext(applicationContext)
            prefix = "classpath:/templates/"
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

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/")

        registry
            .addResourceHandler("/favicon.ico")
            .addResourceLocations("classpath:/static/")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(authInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/login", "/sign_up", "/favicon.ico")
    }
}
