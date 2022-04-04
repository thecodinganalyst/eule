package com.hevlar.accounting.implementation.model

import com.hevlar.accounting.domain.model.BalanceSheet
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

open class GenericBalanceSheet<ACCOUNT_DISPLAY: Any>: BalanceSheet<ACCOUNT_DISPLAY> {

    override lateinit var balanceDate: LocalDate

    override lateinit var currency: Currency

    override lateinit var assets: Map<ACCOUNT_DISPLAY, BigDecimal>

    override lateinit var liabilities: Map<ACCOUNT_DISPLAY, BigDecimal>

}