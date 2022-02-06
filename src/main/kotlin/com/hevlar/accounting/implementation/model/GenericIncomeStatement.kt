package com.hevlar.accounting.implementation.model

import com.hevlar.accounting.domain.model.IncomeStatement
import com.hevlar.accounting.domain.model.account.Account
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

open class GenericIncomeStatement<A: Any, ACCOUNT: Account<A>>: IncomeStatement<A, ACCOUNT> {
    override lateinit var fromDate: LocalDate
    override lateinit var toDate: LocalDate
    override lateinit var currency: Currency
    override lateinit var revenue: Map<ACCOUNT, BigDecimal>
    override lateinit var expenses: Map<ACCOUNT, BigDecimal>
    override lateinit var gains: Map<ACCOUNT, BigDecimal>
    override lateinit var losses: Map<ACCOUNT, BigDecimal>
}