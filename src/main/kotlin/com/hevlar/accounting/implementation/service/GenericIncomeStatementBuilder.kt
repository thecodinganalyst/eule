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

open class GenericIncomeStatementBuilder<A: Any, J: Any, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>> (
    open val generalLedger: GeneralLedger<A, J, ACCOUNT, JOURNAL>,
    open val chartOfAccounts: ChartOfAccounts<A, ACCOUNT>
): IncomeStatementBuilder<A, J, ACCOUNT, JOURNAL> {

    private lateinit var incomeStatement: GenericIncomeStatement<A, ACCOUNT>

    override fun reset(): IncomeStatementBuilder<A, J, ACCOUNT, JOURNAL> {
        incomeStatement = GenericIncomeStatement()
        return this
    }

    override fun setPeriod(fromDate: LocalDate, toDate: LocalDate): IncomeStatementBuilder<A, J, ACCOUNT, JOURNAL> {
        incomeStatement.fromDate = fromDate
        incomeStatement.toDate = toDate
        return this
    }

    override fun setCurrency(currency: Currency): IncomeStatementBuilder<A, J, ACCOUNT, JOURNAL> {
        incomeStatement.currency = currency
        return this
    }

    override fun build(): IncomeStatement<A, ACCOUNT> {
        incomeStatement.revenue = getAccountBalancesForGroup(AccountGroup.Revenue)
        incomeStatement.gains = getAccountBalancesForGroup(AccountGroup.Gains)
        incomeStatement.expenses = getAccountBalancesForGroup(AccountGroup.Expenses)
        incomeStatement.losses = getAccountBalancesForGroup(AccountGroup.Loss)

        return incomeStatement
    }

    private fun getAccountBalancesForGroup(group: AccountGroup): Map<ACCOUNT, BigDecimal> {
        val accounts = chartOfAccounts.getAccountsByGroupAndCurrency(group, incomeStatement.currency)
        return accounts.associateWith { account ->
            (account.openBal ?: BigDecimal(0)) + generalLedger.getBalanceForAccount(account, incomeStatement.currency, incomeStatement.fromDate, incomeStatement.toDate)
        }
    }

}