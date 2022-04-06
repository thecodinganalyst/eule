package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.IncomeStatement
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.journal.JournalEntry
import java.time.LocalDate
import java.util.*

interface IncomeStatementBuilder<ACCOUNT_DISPLAY: Any> {

    fun reset(): IncomeStatementBuilder<ACCOUNT_DISPLAY>

    fun setPeriod(fromDate: LocalDate, toDate: LocalDate): IncomeStatementBuilder<ACCOUNT_DISPLAY>

    fun setCurrency(currency: Currency): IncomeStatementBuilder<ACCOUNT_DISPLAY>

    fun build(): IncomeStatement<ACCOUNT_DISPLAY>

}