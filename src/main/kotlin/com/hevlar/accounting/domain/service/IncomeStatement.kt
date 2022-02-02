package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.account.Account
import java.io.Serializable
import java.math.BigDecimal
import java.time.Period

interface IncomeStatement<A: Serializable> {
    val period: Period
    val revenue: Map<Account<A>, BigDecimal>
    val expenses: Map<Account<A>, BigDecimal>

    fun totalRevenue() = revenue.values.reduce { acc, amount -> acc + amount }
    fun totalExpenses() = expenses.values.reduce { acc, amount -> acc + amount }
    fun profit() = totalRevenue() - totalExpenses()
    fun loss() = -profit()
}