package com.hevlar.eule.repository

import com.hevlar.accounting.implementation.repository.GenericEntryRepository
import com.hevlar.eule.model.PersonalEntry
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface PersonalEntryRepository: GenericEntryRepository<Long, String, PersonalEntry>