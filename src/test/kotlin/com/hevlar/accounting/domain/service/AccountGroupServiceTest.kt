package com.hevlar.accounting.domain.service

import com.hevlar.accounting.domain.model.account.EntryType
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

internal class AccountGroupServiceTest {

    @Test
    fun givenEntryTypeIsEmpty_whenGetAccountGroups_shouldReturnAll() {
        val accountGroups = AccountGroupService().getAccountGroups()
        assertThat(accountGroups.size, equalTo(6))
        assertThat(accountGroups, hasItems("Assets", "Liabilities", "Expenses", "Revenue", "Gains", "Loss"))
    }

    @Test
    fun givenEntryTypeIsDebit_whenGetAccountGroups_shouldReturnAll() {
        val accountGroups = AccountGroupService().getAccountGroups(EntryType.Debit)
        assertThat(accountGroups.size, equalTo(3))
        assertThat(accountGroups, hasItems("Assets", "Expenses", "Loss"))
    }

    @Test
    fun givenEntryTypeIsCredit_whenGetAccountGroups_shouldReturnAll() {
        val accountGroups = AccountGroupService().getAccountGroups(EntryType.Credit)
        assertThat(accountGroups.size, equalTo(3))
        assertThat(accountGroups, hasItems("Liabilities", "Revenue", "Gains"))
    }
}