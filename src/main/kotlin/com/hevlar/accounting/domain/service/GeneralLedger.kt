package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.journal.JournalEntry

interface GeneralLedger<A: Any, J: Any, T: JournalEntry<J, A>> {

    fun get(journalId: J): T?

    fun list(): Collection<T>

    fun exists(journalId: J): Boolean

    fun add(journalEntry: T): T

    fun journalExistsForAccount(accountId: A): Boolean

    fun edit(journalEntry: T): T

}