package com.hevlar.eule.service

import com.hevlar.eule.model.Account
import com.hevlar.eule.model.JournalEntry
import com.hevlar.eule.repository.JournalEntryRepository
import org.springframework.stereotype.Service

@Service
class JournalEntryService (val journalEntryRepository: JournalEntryRepository){

    fun saveJournalEntry(journalEntry: JournalEntry): JournalEntry {
        return journalEntryRepository.save(journalEntry)
    }

    fun deleteJournalEntry(jeId: Long){
        return journalEntryRepository.deleteById(jeId)
    }

    fun getJournalEntry(jeId: Long): JournalEntry {
        return journalEntryRepository.findById(jeId).orElseThrow()
    }

    fun listJournalEntries(): List<JournalEntry>{
        return journalEntryRepository.findAll()
    }

    fun existJournalEntry(jeId: Long): Boolean {
        return journalEntryRepository.existsById(jeId);
    }

}