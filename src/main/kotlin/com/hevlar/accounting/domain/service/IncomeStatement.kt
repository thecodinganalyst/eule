package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.account.Account
import java.io.Serializable
import java.math.BigDecimal
import java.time.Period

interface IncomeStatement<A: Any, ACCOUNT: Account<A>> {
    val period: Period
    val revenue: Map<ACCOUNT, BigDecimal>
    val expenses: Map<ACCOUNT, BigDecimal>

    fun totalRevenue() = revenue.values.reduce { acc, amount -> acc + amount }
    fun totalExpenses() = expenses.values.reduce { acc, amount -> acc + amount }
    fun profit() = totalRevenue() - totalExpenses()
    fun loss() = -profit()
}