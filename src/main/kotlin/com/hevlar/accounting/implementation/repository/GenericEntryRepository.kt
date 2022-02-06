package com.hevlar.accounting.implementation.repository

import com.hevlar.accounting.domain.model.journal.JournalEntry
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*

interface GenericEntryRepository<J :Any, A :Any, JOURNAL : JournalEntry<J, A>>: JpaRepository<JOURNAL, J> {
    fun existsByDebitAccountId(account: A): Boolean
    fun existsByCreditAccountId(account: A): Boolean
    fun findByDebitAccountIdAndCurrencyAndTxDateLessThanEqual(debitAccountId: A, currency: Currency, txDate: LocalDate): Collection<JOURNAL>
    fun findByCreditAccountIdAndCurrencyAndTxDateLessThanEqual(creditAccountId: A, currency: Currency, txDate: LocalDate): Collection<JOURNAL>
    fun findByDebitAccountIdAndCurrencyAndTxDateBetween(debitAccountId: A, currency: Currency, txDateFrom: LocalDate, txDateTo: LocalDate): Collection<JOURNAL>
    fun findByCreditAccountIdAndCurrencyAndTxDateBetween(debitAccountId: A, currency: Currency, txDateFrom: LocalDate, txDateTo: LocalDate): Collection<JOURNAL>
}