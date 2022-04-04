package com.hevlar.eule.controller

import com.hevlar.accounting.domain.model.BalanceSheet
import com.hevlar.eule.model.PersonalAccount
import com.hevlar.eule.service.PersonalBalanceSheetBuilder
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RestController
@RequestMapping("balanceSheet")
class BalanceSheetController(
    val balanceSheetBuilder: PersonalBalanceSheetBuilder
) {
    @GetMapping(value = ["/{currency}/{date}"])
    @ResponseBody
    fun generate(@PathVariable currency: String, @PathVariable date: String): BalanceSheet<String>? {
        return balanceSheetBuilder
            .reset()
            .setCurrency(Currency.getInstance(currency))
            .setBalanceDate(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE))
            .build()
    }
}