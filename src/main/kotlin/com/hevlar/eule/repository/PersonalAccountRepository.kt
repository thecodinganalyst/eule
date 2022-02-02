package com.hevlar.eule.repository

import com.hevlar.accounting.implementation.repository.GenericAccountRepository
import com.hevlar.eule.model.PersonalAccount
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface PersonalAccountRepository: GenericAccountRepository<String, PersonalAccount>