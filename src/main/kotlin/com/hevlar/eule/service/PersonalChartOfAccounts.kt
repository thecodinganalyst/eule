package com.hevlar.eule.service

import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.implementation.GenericChartOfAccounts
import com.hevlar.accounting.implementation.GenericGeneralLedger
import com.hevlar.accounting.implementation.repository.GenericAccountRepository
import com.hevlar.eule.model.PersonalAccount
import com.hevlar.eule.model.PersonalEntry
import com.hevlar.eule.repository.PersonalAccountRepository
import org.springframework.stereotype.Service

@Service
class PersonalChartOfAccounts (
    repository: GenericAccountRepository<String, PersonalAccount>,
    generalLedger: PersonalGeneralLedger
): GenericChartOfAccounts<String, Long, PersonalAccount, PersonalEntry>(repository, generalLedger)