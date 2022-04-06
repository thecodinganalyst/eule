package com.hevlar.accounting.implementation.service

import com.hevlar.accounting.domain.model.IncomeStatement
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.account.AccountGroup
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.service.ChartOfAccounts
import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.accounting.domain.service.IncomeStatementBuilder
import com.hevlar.accounting.implementation.model.GenericIncomeStatement
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

open class GenericIncomeStatementBuilder<A: Any, J: Any, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>, ACCOUNT_DISPLAY: Any> (
    private val generalLedger: GeneralLedger<A, J, ACCOUNT, JOURNAL>,
    private val chartOfAccounts: ChartOfAccounts<A, ACCOUNT>,
    val accountDisplayFunction: (ACCOUNT) -> ACCOUNT_DISPLAY
): IncomeStatementBuilder<ACCOUNT_DISPLAY> {

    private lateinit var incomeStatement: GenericIncomeStatement<ACCOUNT_DISPLAY>

    override fun reset(): IncomeStatementBuilder<ACCOUNT_DISPLAY> {
        incomeStatement = GenericIncomeStatement()
        return this
    }

    override fun setPeriod(fromDate: LocalDate, toDate: LocalDate): IncomeStatementBuilder<ACCOUNT_DISPLAY> {
        incomeStatement.fromDate = fromDate
        incomeStatement.toDate = toDate
        return this
    }

    override fun setCurrency(currency: Currency): IncomeStatementBuilder<ACCOUNT_DISPLAY> {
        incomeStatement.currency = currency
        return this
    }

    override fun build(): IncomeStatement<ACCOUNT_DISPLAY> {
        incomeStatement.revenue = getAccountBalancesForGroup(AccountGroup.Revenue)
        incomeStatement.gains = getAccountBalancesForGroup(AccountGroup.Gains)
        incomeStatement.expenses = getAccountBalancesForGroup(AccountGroup.Expenses)
        incomeStatement.losses = getAccountBalancesForGroup(AccountGroup.Loss)

        return incomeStatement
    }

    private fun getAccountBalancesForGroup(group: AccountGroup): Map<ACCOUNT_DISPLAY, BigDecimal> {
        val accounts = chartOfAccounts.getAccountsByGroup(group)
        return accounts.associateTo(mutableMapOf()) { account ->
            Pair(
                accountDisplayFunction(account),
                (account.openBal ?: BigDecimal(0)) + generalLedger.getBalanceForAccount(account, incomeStatement.currency, incomeStatement.fromDate, incomeStatement.toDate)
            )
        }
    }

}