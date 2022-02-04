package com.hevlar.accounting.implementation

import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.implementation.repository.GenericEntryRepository
import com.hevlar.accounting.domain.service.GeneralLedger
import org.springframework.data.repository.findByIdOrNull
import java.util.NoSuchElementException

open class GenericGeneralLedger<A :Any, J :Any, JOURNAL : JournalEntry<J, A>>(
    private val repository: GenericEntryRepository<J, A, JOURNAL>
) :GeneralLedger<A, J, JOURNAL> {

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
}