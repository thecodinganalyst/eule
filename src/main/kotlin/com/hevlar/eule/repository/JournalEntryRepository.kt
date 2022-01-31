package com.hevlar.eule.repository

import com.hevlar.eule.model.BasicJournalEntry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface JournalEntryRepository: JpaRepository<BasicJournalEntry, Long> {
}