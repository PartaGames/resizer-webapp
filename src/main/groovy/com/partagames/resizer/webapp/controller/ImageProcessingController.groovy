package com.partagames.resizer.webapp.controller

import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class ImageProcessingController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
