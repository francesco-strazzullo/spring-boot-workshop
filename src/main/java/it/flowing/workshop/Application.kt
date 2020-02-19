package it.flowing.workshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
open class Application

fun main(args: Array<String>) {
    System.setProperty("server.servlet.context-path", "/api")
    runApplication<Application>(*args)
}

