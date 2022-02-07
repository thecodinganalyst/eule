package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.BalanceSheet
import com.hevlar.accounting.domain.model.account.Account
import java.time.LocalDate
import java.util.*

interface BalanceSheetBuilder<A: Any, ACCOUNT: Account<A>> {

    fun reset(): BalanceSheetBuilder<A, ACCOUNT>

    fun setBalanceDate(date: LocalDate): BalanceSheetBuilder<A, ACCOUNT>

    fun setCurrency(currency: Currency): BalanceSheetBuilder<A, ACCOUNT>

    fun build(): BalanceSheet<A, ACCOUNT>

}