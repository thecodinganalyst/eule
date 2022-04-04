package com.hevlar.accounting.implementation.repository

import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.account.AccountGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
interface GenericAccountRepository<A: Any, ACCOUNT :Account<A>> :JpaRepository<ACCOUNT, A> {
    fun findByGroupIn(accountGroupList: Collection<AccountGroup>): Collection<ACCOUNT>
    fun findByCurrencyAndGroupIn(currency: Currency, accountGroupList: Collection<AccountGroup>): Collection<ACCOUNT>
}