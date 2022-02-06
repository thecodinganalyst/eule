package com.hevlar.accounting.implementation.service

import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.account.EntryType
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.implementation.repository.GenericEntryRepository
import com.hevlar.accounting.domain.service.GeneralLedger
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

open class GenericGeneralLedger<A :Any, J :Any, ACCOUNT: Account<A>, JOURNAL : JournalEntry<J, A>>(
    private val repository: GenericEntryRepository<J, A, JOURNAL>
) :GeneralLedger<A, J, ACCOUNT, JOURNAL> {

    override fun get(journalId: J): JOURNAL? {
        return repository.findByIdOrNull(journalId)
    }

    override fun list(): Collection<JOURNAL> {
        return repository.findAll()
    }

    override fun exists(journalId: J): Boolean {
        return repository.existsById(journalId)
    }

    override fun add(journalEntry: JOURNAL): JOURNAL {
        return repository.save(journalEntry)
    }

    override fun journalExistsForAccount(accountId: A): Boolean {
        return repository.existsByDebitAccountId(accountId) || repository.existsByCreditAccountId(accountId)
    }

    override fun edit(journalEntry: JOURNAL): JOURNAL {
        if (!exists(journalEntry.id)) throw NoSuchElementException("Journal Entry with id ${journalEntry.id} does not exist")
        return repository.save(journalEntry)
    }

    override fun getBalanceForAccount(account: ACCOUNT, currency: Currency, untilDate: LocalDate): BigDecimal {
        return getTotalAmountForAccountByEntryAndCurrencyUntilDate(account.id, account.group.entryType, currency, untilDate).minus(
            getTotalAmountForAccountByEntryAndCurrencyUntilDate(account.id, EntryType.oppositeOf(account.group.entryType), currency, untilDate)
        )
    }

    override fun getBalanceForAccount(account: ACCOUNT, currency: Currency, fromDate: LocalDate, toDate: LocalDate): BigDecimal {
        return getTotalAmountForAccountByEntryAndCurrencyBetweenDates(account.id, account.group.entryType, currency, fromDate, toDate).minus(
            getTotalAmountForAccountByEntryAndCurrencyBetweenDates(account.id, EntryType.oppositeOf(account.group.entryType), currency, fromDate, toDate)
        )
    }

    override fun getTotalAmountForAccountByEntryAndCurrencyUntilDate(
        accountId: A,
        entryType: EntryType,
        currency: Currency,
        untilDate: LocalDate
    ): BigDecimal {
        return if (entryType == EntryType.Debit){
            repository.findByDebitAccountIdAndCurrencyAndTxDateLessThanEqual(accountId, currency, untilDate)
                .fold(BigDecimal(0)){ acc, item -> acc + item.amount }
        }else {
            repository.findByCreditAccountIdAndCurrencyAndTxDateLessThanEqual(accountId, currency, untilDate)
                .fold(BigDecimal(0)){ acc, item -> acc + item.amount }
        }
    }

    override fun getTotalAmountForAccountByEntryAndCurrencyBetweenDates(
        accountId: A,
        entryType: EntryType,
        currency: Currency,
        fromDate: LocalDate,
        toDate: LocalDate
    ): BigDecimal {
        return if (entryType == EntryType.Debit){
            repository.findByDebitAccountIdAndCurrencyAndTxDateBetween(accountId, currency, fromDate, toDate)
                .fold(BigDecimal(0)){ acc, item -> acc + item.amount }
        }else {
            repository.findByCreditAccountIdAndCurrencyAndTxDateBetween(accountId, currency, fromDate, toDate)
                .fold(BigDecimal(0)){ acc, item -> acc + item.amount }
        }
    }
}