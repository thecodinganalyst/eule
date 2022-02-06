package com.hevlar.accounting.domain.model

import com.hevlar.accounting.domain.model.account.Account
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface IncomeStatement<A: Any, ACCOUNT: Account<A>> {
    val fromDate: LocalDate
    val toDate: LocalDate
    val currency: Currency
    val revenue: Map<ACCOUNT, BigDecimal>
    val expenses: Map<ACCOUNT, BigDecimal>
    val gains: Map<ACCOUNT, BigDecimal>
    val losses: Map<ACCOUNT, BigDecimal>

    fun totalRevenue() = revenue.values.reduce { acc, amount -> acc + amount }
    fun totalExpenses() = expenses.values.reduce { acc, amount -> acc + amount }
    fun totalGains() = gains.values.reduce() { acc, amount -> acc + amount }
    fun totalLosses() = losses.values.reduce() { acc, amount -> acc + amount }
    fun netIncome() = totalRevenue() + totalGains() - totalExpenses() - totalLosses()
}