package com.hevlar.accounting

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@ConstructorBinding
@ConfigurationProperties(prefix = "application.financialyear")
data class FinancialYear(
    var name: String,
    var start: String,
    var end: String,
    var currency: String,
) {
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var startDate: LocalDate? = LocalDate.parse(start, dateFormatter)
    var endDate: LocalDate? = LocalDate.parse(end, dateFormatter)
    var mainCurrency: Currency = Currency.getInstance(currency)
}