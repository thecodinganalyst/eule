package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.account.Account
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface BalanceSheet<A: Any, ACCOUNT: Account<A>> {
    val balanceDate: LocalDate
    val currency: Currency
    val assets: Map<ACCOUNT, BigDecimal>
    val liabilities: Map<ACCOUNT, BigDecimal>

    fun assetsTotal() = assets.values.reduce { acc, amount -> acc + amount }
    fun liabilitiesTotal() = liabilities.values.reduce { acc, amount -> acc + amount }
    fun balance() = assetsTotal() - liabilitiesTotal()
}