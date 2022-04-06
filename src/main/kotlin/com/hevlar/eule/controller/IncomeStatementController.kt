package com.hevlar.eule.controller

import com.hevlar.accounting.domain.model.IncomeStatement
import com.hevlar.eule.service.PersonalIncomeStatementBuilder
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RestController
@RequestMapping("incomeStatement")
class IncomeStatementController(val incomeStatementBuilder: PersonalIncomeStatementBuilder) {
    @GetMapping(value = ["/{currency}/{dateFrom}/{dateTo}"])
    @ResponseBody
    fun generate(@PathVariable currency: String, @PathVariable dateFrom: String, @PathVariable dateTo: String): IncomeStatement<String>? {
        val from = LocalDate.parse(dateFrom, DateTimeFormatter.ISO_LOCAL_DATE)
        val to = LocalDate.parse(dateTo, DateTimeFormatter.ISO_LOCAL_DATE)

        return incomeStatementBuilder
            .reset()
            .setCurrency(Currency.getInstance(currency))
            .setPeriod(from, to)
            .build()
    }
}