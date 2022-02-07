package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.exception.AccountingException
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.account.AccountGroup
import java.util.*

interface ChartOfAccounts<A: Any, ACCOUNT: Account<A>> {

    fun validate(account: ACCOUNT)

    fun exists(accountId: A): Boolean

    fun list(): Collection<ACCOUNT>

    fun get(id: A): ACCOUNT?

    fun add(account: ACCOUNT): ACCOUNT

    fun update(account: ACCOUNT): ACCOUNT

    fun delete(accountId: A)

    fun getBalanceSheetAccounts(): Collection<ACCOUNT>

    fun getIncomeStatementAccounts(): Collection<ACCOUNT>

    fun getAccountsByGroup(accountGroup: AccountGroup): Collection<ACCOUNT>

    fun getAccountsByGroupAndCurrency(accountGroup: AccountGroup, currency: Currency): Collection<ACCOUNT>

}