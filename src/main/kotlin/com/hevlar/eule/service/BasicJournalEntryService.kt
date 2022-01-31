package com.hevlar.eule.service

import com.hevlar.eule.model.BasicJournalEntry
import com.hevlar.eule.repository.JournalEntryRepository
import org.springframework.stereotype.Service

@Service
class BasicJournalEntryService (val journalEntryRepository: JournalEntryRepository){

    fun saveJournalEntry(journalEntry: BasicJournalEntry): BasicJournalEntry {
        return journalEntryRepository.save(journalEntry)
    }

    fun deleteJournalEntry(jeId: Long){
        return journalEntryRepository.deleteById(jeId)
    }

    fun getJournalEntry(jeId: Long): BasicJournalEntry {
        return journalEntryRepository.findById(jeId).orElseThrow()
    }

    fun listJournalEntries(): List<BasicJournalEntry>{
        return journalEntryRepository.findAll()
    }

    fun existJournalEntry(jeId: Long): Boolean {
        return journalEntryRepository.existsById(jeId);
    }

}