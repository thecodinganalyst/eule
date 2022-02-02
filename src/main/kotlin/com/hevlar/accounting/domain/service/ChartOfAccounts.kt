package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.account.Account

interface ChartOfAccounts<A: Any, T: Account<A>> {

    fun exists(accountId: A): Boolean

    fun list(): Collection<T>

    fun get(id: A): T?

    fun add(account: T): T

    fun update(account: T): T

    fun delete(accountId: A)

}