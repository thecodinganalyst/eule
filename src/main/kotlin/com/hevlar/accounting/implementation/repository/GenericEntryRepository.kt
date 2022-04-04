package com.hevlar.accounting.implementation.repository

import com.hevlar.accounting.domain.model.journal.JournalEntry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Repository
@Transactional
interface GenericEntryRepository<J :Any, A :Any, JOURNAL : JournalEntry<J, A>>: JpaRepository<JOURNAL, J> {
    fun existsByDebitAccountId(account: A): Boolean
    fun existsByCreditAccountId(account: A): Boolean
    fun findByDebitAccountIdAndCurrencyAndTxDateLessThanEqual(debitAccountId: A, currency: Currency, txDate: LocalDate): Collection<JOURNAL>
    fun findByCreditAccountIdAndCurrencyAndTxDateLessThanEqual(creditAccountId: A, currency: Currency, txDate: LocalDate): Collection<JOURNAL>
    fun findByDebitAccountIdAndCurrencyAndTxDateBetween(debitAccountId: A, currency: Currency, txDateFrom: LocalDate, txDateTo: LocalDate): Collection<JOURNAL>
    fun findByCreditAccountIdAndCurrencyAndTxDateBetween(debitAccountId: A, currency: Currency, txDateFrom: LocalDate, txDateTo: LocalDate): Collection<JOURNAL>
}