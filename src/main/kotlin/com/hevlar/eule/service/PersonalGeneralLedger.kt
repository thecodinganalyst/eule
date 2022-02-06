package com.hevlar.eule.service

import com.hevlar.accounting.implementation.service.GenericGeneralLedger
import com.hevlar.accounting.implementation.repository.GenericEntryRepository
import com.hevlar.eule.model.PersonalAccount
import com.hevlar.eule.model.PersonalEntry
import org.springframework.stereotype.Service

@Service
class PersonalGeneralLedger (
    repository: GenericEntryRepository<Long, String, PersonalEntry>
) : GenericGeneralLedger<String, Long, PersonalAccount, PersonalEntry>(repository)