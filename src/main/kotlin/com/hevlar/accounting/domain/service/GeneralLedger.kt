package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.journal.JournalEntry

interface GeneralLedger<A: Any, J: Any, JOURNAL: JournalEntry<J, A>> {

    fun get(journalId: J): JOURNAL?

    fun list(): Collection<JOURNAL>

    fun exists(journalId: J): Boolean

    fun add(journalEntry: JOURNAL): JOURNAL

    fun journalExistsForAccount(accountId: A): Boolean

    fun edit(journalEntry: JOURNAL): JOURNAL

}