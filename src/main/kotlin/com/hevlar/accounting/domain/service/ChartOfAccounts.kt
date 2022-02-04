package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.account.Account

interface ChartOfAccounts<A: Any, ACCOUNT: Account<A>> {

    fun exists(accountId: A): Boolean

    fun list(): Collection<ACCOUNT>

    fun get(id: A): ACCOUNT?

    fun add(account: ACCOUNT): ACCOUNT

    fun update(account: ACCOUNT): ACCOUNT

    fun delete(accountId: A)

}