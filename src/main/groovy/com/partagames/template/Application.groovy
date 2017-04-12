package com.partagames.template

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration

/**
 * Created by Antti on 26.10.2015.
 */
@ComponentScan
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
class Application {

    def static main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

}
