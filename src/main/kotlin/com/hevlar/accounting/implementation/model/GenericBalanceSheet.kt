package com.hevlar.accounting.implementation.model

import com.hevlar.accounting.domain.model.BalanceSheet
import com.hevlar.accounting.domain.model.account.Account
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

open class GenericBalanceSheet<A: Any, ACCOUNT: Account<A>>: BalanceSheet<A, ACCOUNT> {

    override lateinit var balanceDate: LocalDate

    override lateinit var currency: Currency

    override lateinit var assets: Map<ACCOUNT, BigDecimal>

    override lateinit var liabilities: Map<ACCOUNT, BigDecimal>

}