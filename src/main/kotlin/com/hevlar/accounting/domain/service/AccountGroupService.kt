package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.account.AccountGroup
import com.hevlar.accounting.domain.model.account.EntryType

open class AccountGroupService {
    fun getAccountGroups(entryType: EntryType): Collection<String>{
        return AccountGroup.values()
            .filter { it.entryType == entryType }
            .map { it.name }
    }

    fun getAccountGroups(): Collection<String>{
        return AccountGroup.values().map { it.name }
    }
}