package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.IncomeStatement
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.journal.JournalEntry
import java.time.LocalDate
import java.util.*

interface IncomeStatementBuilder<A: Any, J: Any, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>> {

    fun reset()

    fun setPeriod(fromDate: LocalDate, toDate: LocalDate)

    fun setCurrency(currency: Currency)

    fun build(): IncomeStatement<A, ACCOUNT>

}