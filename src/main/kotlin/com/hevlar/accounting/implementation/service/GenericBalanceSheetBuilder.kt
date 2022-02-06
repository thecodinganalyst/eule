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

    protected lateinit var balanceSheet: GenericBalanceSheet<A, ACCOUNT>

    override fun reset() {
        balanceSheet = GenericBalanceSheet()
    }

    override fun setBalanceDate(date: LocalDate) {
        balanceSheet.balanceDate = date
    }

    override fun setCurrency(currency: Currency) {
        balanceSheet.currency = currency
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