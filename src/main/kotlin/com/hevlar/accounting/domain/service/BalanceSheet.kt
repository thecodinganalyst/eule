package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.account.Account
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface BalanceSheet<A: Serializable> {
    val balanceDate: LocalDate
    val currency: Currency
    val assets: Map<Account<A>, BigDecimal>
    val liabilities: Map<Account<A>, BigDecimal>

    fun assetsTotal() = assets.values.reduce { acc, amount -> acc + amount }
    fun liabilitiesTotal() = liabilities.values.reduce { acc, amount -> acc + amount }
    fun balance() = assetsTotal() - liabilitiesTotal()
}