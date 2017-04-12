package com.partagames.resizer.webapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@ComponentScan
@EnableAutoConfiguration
@EnableWebMvc
class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

}
