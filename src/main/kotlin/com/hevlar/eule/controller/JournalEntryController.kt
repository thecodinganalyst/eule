package com.hevlar.eule.controller

import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.eule.model.PersonalAccount
import com.hevlar.eule.model.PersonalEntry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("journalEntry")
class JournalEntryController(val service: GeneralLedger<String, Long, PersonalAccount ,PersonalEntry>) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping
    @ResponseBody
    fun listJournalEntries(): Collection<PersonalEntry>{
        return service.list().map { it }
    }

    @GetMapping(value = ["/{jeId}"])
    @ResponseBody
    fun getJournalEntry(@PathVariable jeId: Long): PersonalEntry {
        val result = service.get(jeId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Journal Entry $jeId not found")
        return result
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@RequestBody journalEntry: PersonalEntry): PersonalEntry {
        return service.add(journalEntry)
    }

    @PutMapping
    fun updateAccount(@RequestBody journalEntry: PersonalEntry): PersonalEntry {
        return try {
            service.edit(journalEntry)
        } catch (nse: NoSuchElementException){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, nse.message)
        }
    }

}