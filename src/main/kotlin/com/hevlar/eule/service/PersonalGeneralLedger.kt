package com.hevlar.eule.service

import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.implementation.GenericGeneralLedger
import com.hevlar.accounting.implementation.repository.GenericEntryRepository
import com.hevlar.eule.model.PersonalEntry
import org.springframework.stereotype.Service

@Service
class PersonalGeneralLedger (
    repository: GenericEntryRepository<Long, String, PersonalEntry>
) :GenericGeneralLedger<String, Long, PersonalEntry>(repository)