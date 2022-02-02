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

open class GenericChartOfAccounts<A :Any, J :Any, T: Account<A>, U: JournalEntry<J, A>> (
    private val repository: GenericAccountRepository<A, T>,
    private val generalLedger: GeneralLedger<A, J, U>
) : ChartOfAccounts<A, T> {

    override fun exists(accountId: A): Boolean {
        return repository.existsById(accountId)
    }

    override fun list(): Collection<T> {
        return repository.findAll()
    }

    override fun get(id: A): T? {
        return repository.findByIdOrNull(id)
    }

    override fun add(account: T): T {
        if (exists(account.id)) throw AccountExistException(account.id.toString())
        return repository.save(account)
    }

    override fun update(account: T): T {
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