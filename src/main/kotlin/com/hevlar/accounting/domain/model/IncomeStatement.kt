package com.hevlar.accounting.domain.model

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface IncomeStatement<ACCOUNT_DISPLAY: Any> {
    val fromDate: LocalDate
    val toDate: LocalDate
    val currency: Currency
    val revenue: Map<ACCOUNT_DISPLAY, BigDecimal>
    val expenses: Map<ACCOUNT_DISPLAY, BigDecimal>
    val gains: Map<ACCOUNT_DISPLAY, BigDecimal>
    val losses: Map<ACCOUNT_DISPLAY, BigDecimal>

    fun totalRevenue() = revenue.values.reduce { acc, amount -> acc + amount }
    fun totalExpenses() = expenses.values.reduce { acc, amount -> acc + amount }
    fun totalGains() = gains.values.reduce() { acc, amount -> acc + amount }
    fun totalLosses() = losses.values.reduce() { acc, amount -> acc + amount }
    fun netIncome() = totalRevenue() + totalGains() - totalExpenses() - totalLosses()
}