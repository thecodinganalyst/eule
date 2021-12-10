package com.hevlar.eule.service

import com.hevlar.eule.model.Account
import com.hevlar.eule.model.AccountGroup
import com.hevlar.eule.repository.AccountRepository
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@SpringBootTest
internal class AccountServiceTest {

    private lateinit var accountService: AccountService

    @MockkBean
    private lateinit var accountRepository: AccountRepository

    @BeforeEach
    fun setUp() {
        accountService = AccountService(accountRepository)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun saveAccount() {
        val cash = Account("CASH", "Cash", AccountGroup.Assets, Currency.getInstance("SGD"), BigDecimal(0),  LocalDate.of(2021, 1, 1))
        val result = accountService.saveAccount(cash)
        assertEquals(cash, result)
    }

    @Test
    fun deleteAccount() {
    }

    @Test
    fun getAccount() {
    }

    @Test
    fun listAccounts() {
    }

    @Test
    fun existAccount() {
    }

    @Test
    fun getAccountRepository() {
    }
}