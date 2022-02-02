package com.hevlar.accounting.implementation.repository

import com.hevlar.accounting.domain.model.journal.JournalEntry
import org.springframework.data.jpa.repository.JpaRepository

interface GenericEntryRepository<J :Any, A :Any, T : JournalEntry<J, A>>: JpaRepository<T, J> {
    fun existsByDebitAccountId(account: A): Boolean
    fun existsByCreditAccountId(account: A): Boolean
}