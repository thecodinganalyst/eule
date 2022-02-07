package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.account.EntryType
import com.hevlar.accounting.domain.model.journal.JournalEntry
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface GeneralLedger<A: Any, J: Any, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>> {

    fun validate(journal: JOURNAL)

    fun get(journalId: J): JOURNAL?

    fun list(): Collection<JOURNAL>

    fun exists(journalId: J): Boolean

    fun add(journalEntry: JOURNAL): JOURNAL

    fun journalExistsForAccount(accountId: A): Boolean

    fun edit(journalEntry: JOURNAL): JOURNAL

    fun getBalanceForAccount(account: ACCOUNT, currency: Currency, untilDate: LocalDate): BigDecimal

    fun getBalanceForAccount(account: ACCOUNT, currency: Currency, fromDate: LocalDate, toDate: LocalDate): BigDecimal

    fun getTotalAmountForAccountByEntryAndCurrencyUntilDate(accountId: A , entryType: EntryType, currency: Currency, untilDate: LocalDate): BigDecimal

    fun getTotalAmountForAccountByEntryAndCurrencyBetweenDates(accountId: A , entryType: EntryType, currency: Currency, fromDate: LocalDate, toDate: LocalDate): BigDecimal
}