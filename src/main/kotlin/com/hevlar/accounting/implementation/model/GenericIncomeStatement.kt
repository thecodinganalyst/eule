package com.hevlar.accounting.implementation.model

import com.hevlar.accounting.domain.model.IncomeStatement
import com.hevlar.accounting.domain.model.account.Account
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

open class GenericIncomeStatement<ACCOUNT_DISPLAY: Any>: IncomeStatement<ACCOUNT_DISPLAY> {
    override lateinit var fromDate: LocalDate
    override lateinit var toDate: LocalDate
    override lateinit var currency: Currency
    override lateinit var revenue: Map<ACCOUNT_DISPLAY, BigDecimal>
    override lateinit var expenses: Map<ACCOUNT_DISPLAY, BigDecimal>
    override lateinit var gains: Map<ACCOUNT_DISPLAY, BigDecimal>
    override lateinit var losses: Map<ACCOUNT_DISPLAY, BigDecimal>
}