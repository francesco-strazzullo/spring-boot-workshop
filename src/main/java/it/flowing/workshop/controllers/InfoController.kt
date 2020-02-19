package it.flowing.workshop.controllers

import it.flowing.workshop.ApiVersion
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration
import java.time.LocalDateTime

@RestController
@RequestMapping(value = ["/" + ApiVersion.V1 + "/info"])
@SkipAuthentication
class InfoController {
    private val startupTime = LocalDateTime.now()
    @GetMapping
    fun uptime(): Long {
        val uptime = Duration.between(startupTime, LocalDateTime.now())
        return uptime.toMillis()
    }
}