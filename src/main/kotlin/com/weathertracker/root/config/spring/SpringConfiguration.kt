package com.weathertracker.root.config.spring

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.weathertracker.root.interceptor.AuthenticatedUserRedirectInterceptor
import com.weathertracker.root.interceptor.UnauthenticatedRedirectInterceptor
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.servlet.config.annotation.*
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring6.view.ThymeleafViewResolver

@Configuration
@ComponentScan("com.weathertracker.root")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableWebMvc
class SpringConfiguration(
    private val applicationContext: ApplicationContext,
    private val unauthenticatedRedirectInterceptor: UnauthenticatedRedirectInterceptor,
    private val authenticatedUserRedirectInterceptor: AuthenticatedUserRedirectInterceptor,
) : WebMvcConfigurer {
    @Bean
    fun templateResolver(): SpringResourceTemplateResolver =
        SpringResourceTemplateResolver().apply {
            setApplicationContext(applicationContext)
            prefix = "classpath:/templates/"
            suffix = ".html"
            isCacheable = false
            characterEncoding = "UTF-8"
        }

    @Bean
    fun templateEngine(): SpringTemplateEngine =
        SpringTemplateEngine().apply {
            setTemplateResolver(templateResolver())
            enableSpringELCompiler = true
        }

    @Bean
    fun jacksonMapper(): ObjectMapper =
        jacksonObjectMapper().apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }

    @Bean
    fun webClient() = WebClient.create("http://api.openweathermap.org")

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        registry.viewResolver(
            ThymeleafViewResolver().apply {
                templateEngine = templateEngine()
                characterEncoding = "UTF-8"
            },
        )
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
            .addInterceptor(unauthenticatedRedirectInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/login", "/sign_up", "/favicon.ico")
        registry
            .addInterceptor(authenticatedUserRedirectInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/index", "/", "/search", "/weather", "/favicon.ico", "/logout")
    }
}
