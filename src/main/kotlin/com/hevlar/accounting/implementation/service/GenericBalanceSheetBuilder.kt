package com.hevlar.accounting.implementation.service

import com.hevlar.accounting.domain.model.BalanceSheet
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.account.AccountGroup
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.service.BalanceSheetBuilder
import com.hevlar.accounting.domain.service.ChartOfAccounts
import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.accounting.implementation.model.GenericBalanceSheet
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

open class GenericBalanceSheetBuilder<A :Any, J :Any, ACCOUNT: Account<A>, JOURNAL : JournalEntry<J, A>>(
    open val generalLedger: GeneralLedger<A, J, ACCOUNT, JOURNAL>,
    open val chartOfAccounts: ChartOfAccounts<A, ACCOUNT>
): BalanceSheetBuilder<A, ACCOUNT> {

    private lateinit var balanceSheet: GenericBalanceSheet<A, ACCOUNT>

    override fun reset(): BalanceSheetBuilder<A, ACCOUNT> {
        balanceSheet = GenericBalanceSheet()
        return this
    }

    override fun setBalanceDate(date: LocalDate): BalanceSheetBuilder<A, ACCOUNT> {
        balanceSheet.balanceDate = date
        return this
    }

    override fun setCurrency(currency: Currency): BalanceSheetBuilder<A, ACCOUNT> {
        balanceSheet.currency = currency
        return this
    }

    override fun build(): BalanceSheet<A, ACCOUNT> {
        balanceSheet.assets = getAccountBalancesForGroup(AccountGroup.Assets)
        balanceSheet.liabilities = getAccountBalancesForGroup(AccountGroup.Liabilities)
        return balanceSheet
    }

    private fun getAccountBalancesForGroup(group: AccountGroup): Map<ACCOUNT, BigDecimal> {
        val accounts = chartOfAccounts.getAccountsByGroupAndCurrency(group, balanceSheet.currency)
        return accounts.associateWith { account ->
            (account.openBal ?: BigDecimal(0)) + generalLedger.getBalanceForAccount(account, balanceSheet.currency, balanceSheet.balanceDate)
        }
    }

}