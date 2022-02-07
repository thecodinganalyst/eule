package com.hevlar.accounting.domain.controller

import com.hevlar.accounting.domain.exception.*
import com.hevlar.accounting.domain.model.BalanceSheet
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.service.BalanceSheetBuilder
import com.hevlar.accounting.domain.service.ChartOfAccounts
import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.accounting.domain.service.IncomeStatementBuilder
import java.time.LocalDate
import java.util.*

interface FinancialYear<A: Any, J: Any, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>>
{
    val name: String
    val startDate: LocalDate
    val mainCurrency: Currency

    val chartOfAccounts: ChartOfAccounts<A, ACCOUNT>
    val generalLedger: GeneralLedger<A, J, ACCOUNT, JOURNAL>
    val balanceSheetBuilder: BalanceSheetBuilder<A, ACCOUNT>
    val incomeStatementBuilder: IncomeStatementBuilder<A, J, ACCOUNT, JOURNAL>

    val endDate: LocalDate
        get() = startDate.plusYears(1).minusDays(1)

    fun checkAccount(account: ACCOUNT){
        val collector = GroupAccountingException()

        if (account.openDate?.isBefore(startDate) == true) collector.add(BalanceSheetAccountOpenDateMissing())
        if (account.openDate?.isAfter(endDate) == true) collector.add(BalanceSheetAccountOpenDateMissing())

        collector.throwIfNotEmpty()
    }

    fun createAccount(account: ACCOUNT): ACCOUNT {
        checkAccount(account)
        return chartOfAccounts.add(account)
    }

    fun getAccount(accountId: A): ACCOUNT? {
        return chartOfAccounts.get(accountId)
    }

    fun listAccounts(): Collection<ACCOUNT> {
        return chartOfAccounts.list()
    }

    fun updateAccount(account: ACCOUNT): ACCOUNT {
        checkAccount(account)
        return chartOfAccounts.update(account)
    }

    fun deleteAccount(accountId: A) {
        chartOfAccounts.delete(accountId)
    }

    fun checkEntry(entry: JOURNAL) {
        val collector = GroupAccountingException()

        if (entry.txDate.isBefore(startDate)) collector.add(JournalTxDateBeforeFY())
        if (entry.txDate.isAfter(endDate)) collector.add(JournalTxDateAfterFY())

        collector.throwIfNotEmpty()
    }

    fun createEntry(entry: JOURNAL): JOURNAL {
        checkEntry(entry)
        return generalLedger.add(entry)
    }

    fun getEntry(entryId: J): JOURNAL? {
        return generalLedger.get(entryId)
    }

    fun listEntries(): Collection<JOURNAL> {
        return generalLedger.list()
    }

    fun updateEntry(entry: JOURNAL): JOURNAL {
        checkEntry(entry)
        return generalLedger.edit(entry)
    }

    fun generateBalanceSheet(currency: Currency, balanceDate: LocalDate): BalanceSheet<A, ACCOUNT> {
        return balanceSheetBuilder
            .setCurrency(currency)
            .setBalanceDate(balanceDate)
            .build()
    }

    fun generateBalanceSheet(balanceDate: LocalDate): BalanceSheet<A, ACCOUNT> = generateBalanceSheet(mainCurrency, balanceDate)


}