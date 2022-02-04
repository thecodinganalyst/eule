package com.hevlar.accounting.domain.controller

import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.service.BalanceSheet
import com.hevlar.accounting.domain.service.ChartOfAccounts
import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.accounting.domain.service.IncomeStatement
import java.io.Serializable
import java.time.LocalDate
import java.time.Period
import java.util.*

interface FinancialYear<A: Serializable, J: Serializable, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>> {

    val name: String
    val period: Period
    val chartOfAccounts: ChartOfAccounts<A, ACCOUNT>
    val generalLedger: GeneralLedger<A, J, JOURNAL>

    fun generateBalanceSheet(balanceDate: LocalDate, currency: Currency): BalanceSheet<A, ACCOUNT>

    fun generateIncomeStatement(period: Period, currency: Currency): IncomeStatement<A, ACCOUNT>

}