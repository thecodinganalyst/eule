package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.IncomeStatement
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.journal.JournalEntry
import java.time.LocalDate
import java.util.*

interface IncomeStatementBuilder<A: Any, J: Any, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>> {

    fun reset(): IncomeStatementBuilder<A, J, ACCOUNT, JOURNAL>

    fun setPeriod(fromDate: LocalDate, toDate: LocalDate): IncomeStatementBuilder<A, J, ACCOUNT, JOURNAL>

    fun setCurrency(currency: Currency): IncomeStatementBuilder<A, J, ACCOUNT, JOURNAL>

    fun build(): IncomeStatement<A, ACCOUNT>

}