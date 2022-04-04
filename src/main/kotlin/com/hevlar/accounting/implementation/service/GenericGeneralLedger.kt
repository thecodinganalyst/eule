package com.hevlar.accounting.implementation.service

import com.hevlar.accounting.FinancialYear
import com.hevlar.accounting.domain.exception.*
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
    private val repository: GenericEntryRepository<J, A, JOURNAL>,
    private val financialYear: FinancialYear
) :GeneralLedger<A, J, ACCOUNT, JOURNAL> {

    override fun validate(journal: JOURNAL) {
        val collector = GroupAccountingException()
        if (journal.txDate.isBefore(financialYear.startDate)) collector.add(JournalTxDateBeforeFY())
        if (journal.txDate.isAfter(financialYear.endDate)) collector.add(JournalTxDateAfterFY())
        collector.throwIfNotEmpty()
    }

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
        validate(journalEntry)
        return repository.save(journalEntry)
    }

    override fun journalExistsForAccount(accountId: A): Boolean {
        return repository.existsByDebitAccountId(accountId) || repository.existsByCreditAccountId(accountId)
    }

    override fun edit(journalEntry: JOURNAL): JOURNAL {
        validate(journalEntry)
        if (!exists(journalEntry.id)) throw NoSuchElementException("Journal Entry with id ${journalEntry.id} does not exist")
        return repository.save(journalEntry)
    }

    override fun getBalanceForAccount(account: ACCOUNT, currency: Currency, untilDate: LocalDate): BigDecimal {
        val debit = repository
            .findByDebitAccountIdAndCurrencyAndTxDateLessThanEqual(account.id, currency, untilDate)
            .fold(BigDecimal(0)){ acc, item -> acc + item.amount }
        val credit = repository
            .findByCreditAccountIdAndCurrencyAndTxDateLessThanEqual(account.id, currency, untilDate)
            .fold(BigDecimal(0)){ acc, item -> acc + item.amount }

        return if (account.group.entryType == EntryType.Debit){
            debit - credit
        }else{
            credit - debit
        }
    }

    override fun getBalanceForAccount(account: ACCOUNT, currency: Currency, fromDate: LocalDate, toDate: LocalDate): BigDecimal {

        val debit = repository
            .findByDebitAccountIdAndCurrencyAndTxDateBetween(account.id, currency, fromDate, toDate)
            .fold(BigDecimal(0)){ acc, item -> acc + item.amount }
        val credit = repository
            .findByCreditAccountIdAndCurrencyAndTxDateBetween(account.id, currency, fromDate, toDate)
            .fold(BigDecimal(0)){ acc, item -> acc + item.amount }

        return if (account.group.entryType == EntryType.Debit){
            debit - credit
        }else{
            credit - debit
        }

    }

}