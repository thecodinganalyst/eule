package com.hevlar.eule.controller

import com.hevlar.accounting.domain.service.AccountGroupService
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

internal class AccountGroupControllerTest{

    val accountGroupService = AccountGroupService()
    val accountGroupController = AccountGroupController(accountGroupService)

    @Test
    fun givenEntryTypeIsEmpty_whenGetAccountGroups_shouldReturnAll(){
        val results = accountGroupController.getAccountGroups(null)
        assertThat(results.size, equalTo(6))
        assertThat(results, hasItems("Assets", "Liabilities", "Expenses", "Revenue", "Gains", "Loss")
        )
    }

    @Test
    fun givenEntryTypeIsDebit_whenGetAccountGroups_shouldReturnAll(){
        val results = accountGroupController.getAccountGroups("debit")
        assertThat(results.size, equalTo(3))
        assertThat(results, hasItems("Assets", "Expenses", "Loss")
        )
    }

    @Test
    fun givenEntryTypeIsCredit_whenGetAccountGroups_shouldReturnAll(){
        val results = accountGroupController.getAccountGroups("Credit")
        assertThat(results.size, equalTo(3))
        assertThat(results, hasItems("Liabilities", "Revenue", "Gains")
        )
    }

}