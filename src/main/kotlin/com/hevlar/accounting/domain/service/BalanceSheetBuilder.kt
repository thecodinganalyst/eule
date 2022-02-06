package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.BalanceSheet
import com.hevlar.accounting.domain.model.account.Account
import java.time.LocalDate
import java.util.*

interface BalanceSheetBuilder<A: Any, ACCOUNT: Account<A>> {

    fun reset()

    fun setBalanceDate(date: LocalDate)

    fun setCurrency(currency: Currency)

    fun build(): BalanceSheet<A, ACCOUNT>

}