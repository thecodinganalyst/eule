package com.hevlar.eule

import com.hevlar.accounting.FinancialYear
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(FinancialYear::class)
class EuleApplication

fun main(args: Array<String>) {
    runApplication<EuleApplication>(*args)
}
