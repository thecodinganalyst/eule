package com.hevlar.accounting.implementation

import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.exception.AccountAlreadyInUseException
import com.hevlar.accounting.domain.exception.AccountExistException
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.service.ChartOfAccounts
import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.accounting.implementation.repository.GenericAccountRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.NoSuchElementException

open class GenericChartOfAccounts<A :Any, J :Any, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>> (
    private val repository: GenericAccountRepository<A, ACCOUNT>,
    private val generalLedger: GeneralLedger<A, J, JOURNAL>
) : ChartOfAccounts<A, ACCOUNT> {

    override fun exists(accountId: A): Boolean {
        return repository.existsById(accountId)
    }

    override fun list(): Collection<ACCOUNT> {
        return repository.findAll()
    }

    override fun get(id: A): ACCOUNT? {
        return repository.findByIdOrNull(id)
    }

    override fun add(account: ACCOUNT): ACCOUNT {
        if (exists(account.id)) throw AccountExistException(account.id.toString())
        return repository.save(account)
    }

    override fun update(account: ACCOUNT): ACCOUNT {
        if (!exists(account.id)) throw NoSuchElementException("Account with id ${account.id} does not exists")
        if (generalLedger.journalExistsForAccount(account.id)) throw AccountAlreadyInUseException(account.id.toString())
        return repository.save(account)
    }

    override fun delete(accountId: A) {
        if (!exists(accountId)) throw NoSuchElementException("Account with id $accountId does not exists")
        if (generalLedger.journalExistsForAccount(accountId)) throw AccountAlreadyInUseException(accountId.toString())
        repository.deleteById(accountId)
    }
}