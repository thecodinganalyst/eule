package com.hevlar.accounting.domain.controller

import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.service.BalanceSheetBuilder
import com.hevlar.accounting.domain.service.ChartOfAccounts
import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.accounting.domain.service.IncomeStatementBuilder
import java.io.Serializable
import java.time.Period

interface FinancialYear<A: Serializable, J: Serializable, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>>
{

    val name: String
    val period: Period
    val chartOfAccounts: ChartOfAccounts<A, ACCOUNT>
    val generalLedger: GeneralLedger<A, J, ACCOUNT, JOURNAL>
    val balanceSheetBuilder: BalanceSheetBuilder<A, ACCOUNT>
    val incomeStatementBuilder: IncomeStatementBuilder<A, J, ACCOUNT, JOURNAL>

}