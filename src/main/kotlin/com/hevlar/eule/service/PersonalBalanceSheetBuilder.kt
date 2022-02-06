package com.hevlar.eule.service

import com.hevlar.accounting.domain.service.ChartOfAccounts
import com.hevlar.accounting.implementation.service.GenericBalanceSheetBuilder
import com.hevlar.eule.model.PersonalAccount
import com.hevlar.eule.model.PersonalEntry

class PersonalBalanceSheetBuilder(
    override val generalLedger: PersonalGeneralLedger,
    override val chartOfAccounts: PersonalChartOfAccounts
): GenericBalanceSheetBuilder<String, Long, PersonalAccount, PersonalEntry>(generalLedger, chartOfAccounts) {
}