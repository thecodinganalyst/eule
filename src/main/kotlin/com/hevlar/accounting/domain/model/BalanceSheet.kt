package com.hevlar.accounting.domain.model

import com.hevlar.accounting.domain.model.account.Account
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface BalanceSheet<A: Any, ACCOUNT: Account<A>> {
    var balanceDate: LocalDate
    var currency: Currency
    var assets: Map<ACCOUNT, BigDecimal>
    var liabilities: Map<ACCOUNT, BigDecimal>

    fun assetsTotal() = assets.values.reduce{ acc, amount -> acc + amount }
    fun liabilitiesTotal() = liabilities.values.reduce { acc, amount -> acc + amount }
    fun balance() = assetsTotal() - liabilitiesTotal()
}