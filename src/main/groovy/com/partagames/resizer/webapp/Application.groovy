package com.partagames.resizer.webapp

import com.partagames.imageresizetool.ImageScaleTool
import com.partagames.resizer.webapp.storage.StorageProperties
import com.partagames.resizer.webapp.storage.StorageService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        { args ->
            storageService.deleteAll()
            storageService.init()
        }
    }

    @Bean
    ImageScaleTool imageScaleTool() {
        return new ImageScaleTool()
    }

}
