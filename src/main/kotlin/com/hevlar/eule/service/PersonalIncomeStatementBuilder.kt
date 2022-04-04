package com.hevlar.eule.service

import com.hevlar.accounting.implementation.service.GenericIncomeStatementBuilder
import com.hevlar.eule.model.PersonalAccount
import com.hevlar.eule.model.PersonalEntry
import org.springframework.stereotype.Service

@Service
class PersonalIncomeStatementBuilder(
    override val generalLedger: PersonalGeneralLedger,
    override val chartOfAccounts: PersonalChartOfAccounts
) : GenericIncomeStatementBuilder<String, Long, PersonalAccount, PersonalEntry>(
    generalLedger,
    chartOfAccounts
) {
}