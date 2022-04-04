package com.hevlar.eule.service

import com.hevlar.accounting.implementation.service.GenericBalanceSheetBuilder
import com.hevlar.eule.model.PersonalAccount
import com.hevlar.eule.model.PersonalEntry
import org.springframework.stereotype.Service

@Service
class PersonalBalanceSheetBuilder(
    val generalLedger: PersonalGeneralLedger,
    val chartOfAccounts: PersonalChartOfAccounts
): GenericBalanceSheetBuilder<String, Long, PersonalAccount, PersonalEntry, String>(
    generalLedger,
    chartOfAccounts,
    { PersonalAccount -> PersonalAccount.name }
)