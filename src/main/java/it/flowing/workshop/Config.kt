package it.flowing.workshop

import it.flowing.workshop.interceptors.AuthenticationInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.sql.DataSource

@Configuration
@PropertySource("classpath:database.properties")
open class Config : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthenticationInterceptor())
    }

    @Value("\${database.url}")
    private val url: String? = null
    @Value("\${database.username}")
    private val username: String? = null
    @Value("\${database.password}")
    private val password: String? = null

    @Bean
    open fun dataSource(): DataSource {
        return DataSourceBuilder
                .create()
                .username(username) // TODO: Get from properties
                .password(password) // TODO: Get from properties
                .url(url) // TODO: Get from properties
                .build()
    }
}