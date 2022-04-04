package com.hevlar.accounting.domain.model

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface BalanceSheet<ACCOUNT_DISPLAY: Any> {
    var balanceDate: LocalDate
    var currency: Currency
    var assets: Map<ACCOUNT_DISPLAY, BigDecimal>
    var liabilities: Map<ACCOUNT_DISPLAY, BigDecimal>

    fun assetsTotal() = assets.values.reduce{ acc, amount -> acc + amount }
    fun liabilitiesTotal() = liabilities.values.reduce { acc, amount -> acc + amount }
    fun balance() = assetsTotal() - liabilitiesTotal()
}