package it.flowing.workshop

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:database.properties")
class DBConfig {
    @JvmField
    @Value("\${database.url}")
    val url: String? = null
    @JvmField
    @Value("\${database.username}")
    val username: String? = null
    @JvmField
    @Value("\${database.password}")
    val password: String? = null
}