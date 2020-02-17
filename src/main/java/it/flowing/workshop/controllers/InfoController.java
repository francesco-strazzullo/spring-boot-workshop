package it.flowing.workshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

import static it.flowing.workshop.ApiVersion.V1;

@RestController
@RequestMapping(value = "/" + V1 + "/info")
@SkipAuthentication
public class InfoController {
    private LocalDateTime startupTime = LocalDateTime.now();

    @GetMapping
    public long uptime() {
        Duration uptime = Duration.between(startupTime, LocalDateTime.now());
        return uptime.toMillis();
    }
}
