package com.hevlar.accounting.implementation.service

import com.hevlar.accounting.FinancialYear
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.accounting.implementation.repository.GenericAccountRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GenericChartOfAccountsTest {

    val repository: GenericAccountRepository<String, Account<String>> = mockk()
    val generalLedger: GeneralLedger<String, Long, Account<String>, JournalEntry<Long, String>> = mockk()
    val financialYear: FinancialYear = mockk()
    val chartOfAccounts = GenericChartOfAccounts<String, Long, Account<String>, JournalEntry<Long, String>>(repository, generalLedger, financialYear)

    @Test
    fun validate() {

    }

    @Test
    fun exists() {
        // Given
        every { repository.existsById("cash") } returns true

        // When
        val result = chartOfAccounts.exists("cash")

        // Then
        verify(exactly = 1) { repository.existsById("cash") }
        assertTrue(result)

    }

    @Test
    fun list() {
    }

    @Test
    fun get() {
    }

    @Test
    fun add() {
    }

    @Test
    fun update() {
    }

    @Test
    fun delete() {
    }

    @Test
    fun getAccountsByGroup() {
    }

    @Test
    fun getAccountsByGroupAndCurrency() {
    }

    @Test
    fun getBalanceSheetAccounts() {
    }

    @Test
    fun getIncomeStatementAccounts() {
    }
}