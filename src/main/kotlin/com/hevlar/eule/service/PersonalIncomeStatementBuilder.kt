package com.hevlar.eule.service

import com.hevlar.accounting.implementation.service.GenericIncomeStatementBuilder
import com.hevlar.eule.model.PersonalAccount
import com.hevlar.eule.model.PersonalEntry
import org.springframework.stereotype.Service

@Service
class PersonalIncomeStatementBuilder(
    val generalLedger: PersonalGeneralLedger,
    val chartOfAccounts: PersonalChartOfAccounts
): GenericIncomeStatementBuilder<String, Long, PersonalAccount, PersonalEntry, String>(
    generalLedger,
    chartOfAccounts,
    { PersonalAccount -> PersonalAccount.name }
)