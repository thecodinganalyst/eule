package com.hevlar.eule.controller

import com.hevlar.eule.model.JournalEntry
import com.hevlar.eule.service.JournalEntryService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("journalEntry")
class JournalEntryController(val journalEntryService: JournalEntryService) {

    val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping
    @ResponseBody
    fun listJournalEntries(): List<JournalEntry>{
        return journalEntryService.listJournalEntries()
    }

    @GetMapping(value = ["/{jeId}"])
    @ResponseBody
    fun getJournalEntry(@PathVariable jeId: Long): JournalEntry {
        return try{
            journalEntryService.getJournalEntry(jeId)
        }catch (e: Exception){
            logger.error("Journal Entry $jeId not found")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Journal Entry $jeId not found")
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@RequestBody journalEntry: JournalEntry): JournalEntry {
        if(journalEntryService.existJournalEntry(journalEntry.id)){
            logger.error("Journal Entry - ${journalEntry.id} already exists")
            throw ResponseStatusException(HttpStatus.CONFLICT, "Journal Entry - ${journalEntry.id} already exists")
        }


        return try{
            journalEntryService.saveJournalEntry(journalEntry)

        } catch (e: Exception) {
            logger.error("Invalid Journal Entry - ${journalEntry.id}")
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Journal Entry - ${journalEntry.id}")
        }
    }

    @PutMapping
    fun updateAccount(@RequestBody journalEntry: JournalEntry): JournalEntry? {
        return if (journalEntryService.existJournalEntry(journalEntry.id)){
            journalEntryService.saveJournalEntry(journalEntry)
        }else{
            logger.error("Journal Entry ${journalEntry.id} not found")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Journal Entry ${journalEntry.id} not found")
        }
    }

    @DeleteMapping(value = ["/{jeId}"])
    @ResponseStatus(HttpStatus.OK)
    fun deleteAccount(@PathVariable jeId: Long): Boolean{
        return if (journalEntryService.existJournalEntry(jeId)){
            journalEntryService.deleteJournalEntry(jeId)
            true
        }else{
            logger.error("Account $jeId not found")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account $jeId not found")
        }
    }
}