package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.BalanceSheet
import java.time.LocalDate
import java.util.*

interface BalanceSheetBuilder<ACCOUNT_DISPLAY: Any> {

    fun reset(): BalanceSheetBuilder<ACCOUNT_DISPLAY>

    fun setBalanceDate(date: LocalDate): BalanceSheetBuilder<ACCOUNT_DISPLAY>

    fun setCurrency(currency: Currency): BalanceSheetBuilder<ACCOUNT_DISPLAY>

    fun build(): BalanceSheet<ACCOUNT_DISPLAY>

}