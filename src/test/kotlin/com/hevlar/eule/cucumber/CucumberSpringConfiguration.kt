package com.hevlar.eule.cucumber

import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
class CucumberSpringConfiguration {

    @Test
    fun contextLoads(){

    }
}