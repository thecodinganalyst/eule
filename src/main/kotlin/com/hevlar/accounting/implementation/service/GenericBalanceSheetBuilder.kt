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

open class GenericBalanceSheetBuilder<A :Any, J :Any, ACCOUNT: Account<A>, JOURNAL : JournalEntry<J, A>, ACCOUNT_DISPLAY: Any>(
    private val generalLedger: GeneralLedger<A, J, ACCOUNT, JOURNAL>,
    private val chartOfAccounts: ChartOfAccounts<A, ACCOUNT>,
    val accountDisplayFunction: (ACCOUNT) -> ACCOUNT_DISPLAY
): BalanceSheetBuilder<ACCOUNT_DISPLAY> {

    open lateinit var balanceSheet: GenericBalanceSheet<ACCOUNT_DISPLAY>

    override fun reset(): BalanceSheetBuilder<ACCOUNT_DISPLAY> {
        balanceSheet = GenericBalanceSheet()
        return this
    }

    override fun setBalanceDate(date: LocalDate): BalanceSheetBuilder<ACCOUNT_DISPLAY> {
        balanceSheet.balanceDate = date
        return this
    }

    override fun setCurrency(currency: Currency): BalanceSheetBuilder<ACCOUNT_DISPLAY> {
        balanceSheet.currency = currency
        return this
    }

    override fun build(): BalanceSheet<ACCOUNT_DISPLAY> {
        balanceSheet.assets = getAccountBalancesForGroup(AccountGroup.Assets)
        balanceSheet.liabilities = getAccountBalancesForGroup(AccountGroup.Liabilities)
        return balanceSheet
    }

    open fun getAccountBalancesForGroup(group: AccountGroup): Map<ACCOUNT_DISPLAY, BigDecimal> {
        val accounts = chartOfAccounts.getAccountsByGroupAndCurrency(group, balanceSheet.currency)
        return accounts.associateTo(mutableMapOf()) { account ->
            Pair(
                accountDisplayFunction(account),
                (account.openBal ?: BigDecimal(0)) + generalLedger.getBalanceForAccount(account, balanceSheet.currency, balanceSheet.balanceDate)
            )
        }
    }

}