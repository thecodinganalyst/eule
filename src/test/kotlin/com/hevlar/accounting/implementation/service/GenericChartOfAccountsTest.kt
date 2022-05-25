package com.hevlar.accounting.implementation.service

import com.hevlar.accounting.FinancialYear
import com.hevlar.accounting.domain.exception.*
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.account.AccountGroup
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.accounting.implementation.repository.GenericAccountRepository
import com.hevlar.eule.model.PersonalAccount
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.NoSuchElementException

internal class GenericChartOfAccountsTest {

    val repository: GenericAccountRepository<String, Account<String>> = mockk()
    val generalLedger: GeneralLedger<String, Long, Account<String>, JournalEntry<Long, String>> = mockk()
    val financialYear: FinancialYear = FinancialYear("2022", "2022-01-01", "2022-12-31", "SGD")
    val chartOfAccounts = GenericChartOfAccounts(repository, generalLedger, financialYear)

    @Test
    fun givenAccountGroupIsAssetAndOpenDateIsMissing_whenValidate_shouldThrow() {
        val account = PersonalAccount(
            "Cash",
            "Cash",
            AccountGroup.Assets,
            Currency.getInstance("SGD"),
            BigDecimal("100"),
            null,
            null,
            null
        )
        val exception = assertThrows(GroupAccountingException::class.java){
            chartOfAccounts.validate(account)
        }

        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountOpenDateMissing::class.java)))
    }

    @Test
    fun givenAccountGroupIsAssetAndOpeBalIsMissing_whenValidate_shouldThrow() {
        val account = PersonalAccount(
            "Cash",
            "Cash",
            AccountGroup.Assets,
            Currency.getInstance("SGD"),
            null,
            LocalDate.now(),
            null,
            null
        )
        val exception = assertThrows(GroupAccountingException::class.java){
            chartOfAccounts.validate(account)
        }

        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountOpenBalMissing::class.java)))
    }

    @Test
    fun givenAccountGroupIsAssetAndCurrencyIsMissing_whenValidate_shouldThrow() {
        val account = PersonalAccount(
            "Cash",
            "Cash",
            AccountGroup.Assets,
            null,
            BigDecimal("100"),
            LocalDate.now(),
            null,
            null
        )
        val exception = assertThrows(GroupAccountingException::class.java){
            chartOfAccounts.validate(account)
        }

        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountCurrencyMissing::class.java)))
    }

    @Test
    fun givenAccountGroupIsAssetAndOpenDateIsBeforeFyStartDate_whenValidate_shouldThrow() {
        val account = PersonalAccount(
            "Cash",
            "Cash",
            AccountGroup.Assets,
            null,
            BigDecimal("100"),
            LocalDate.of(2021, 1, 1),
            null,
            null
        )
        val exception = assertThrows(GroupAccountingException::class.java){
            chartOfAccounts.validate(account)
        }

        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountOpenDateBeforeFY::class.java)))
    }

    @Test
    fun givenAccountGroupIsAssetAndOpenDateIsAfterFyEndDate_whenValidate_shouldThrow() {
        val account = PersonalAccount(
            "Cash",
            "Cash",
            AccountGroup.Assets,
            null,
            BigDecimal("100"),
            LocalDate.of(2023, 1, 1),
            null,
            null
        )
        val exception = assertThrows(GroupAccountingException::class.java){
            chartOfAccounts.validate(account)
        }

        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountOpenDateAfterFY::class.java)))
    }

    @Test
    fun givenAccountExistsInRepository_whenExist_shouldReturnTrue() {
        // Given
        every { repository.existsById("cash") } returns true

        // When
        val result = chartOfAccounts.exists("cash")

        // Then
        verify(exactly = 1) { repository.existsById("cash") }

        assertThat(result, equalTo(true))
    }

    @Test
    fun givenAccountDoesNotExistsInRepository_whenExist_shouldReturnFalse() {

        // Given
        every { repository.existsById("cash") } returns false

        // When
        val result = chartOfAccounts.exists("cash")

        // Then
        verify(exactly = 1) { repository.existsById("cash") }

        assertThat(result, equalTo(false))
    }

    @Test
    fun givenThreeAccountsInRepository_whenList_shouldReturnThree() {

        // Given
        val cash = PersonalAccount("cash", "cash", AccountGroup.Assets, Currency.getInstance("SGD"), BigDecimal("100"), LocalDate.now())
        val food = PersonalAccount("food", "food", AccountGroup.Expenses)
        val accounts = listOf(cash, food)
        every { repository.findAll() } returns accounts

        // When
        val result = chartOfAccounts.list()

        // Then
        verify(exactly = 1){ repository.findAll() }

        assertThat(result.size, equalTo(2))
        assertThat(result, hasItem(cash))
        assertThat(result, hasItem(food))
    }

    @Test
    fun givenAccountExists_whenGet_shouldReturnAccount() {

        // Given
        val food = PersonalAccount("food", "food", AccountGroup.Expenses)
        every { repository.findByIdOrNull("food") } returns food

        // When
        val result = chartOfAccounts.get("food")

        // Then
        verify(exactly = 1){ repository.findByIdOrNull("food") }
        assertThat(result, equalTo(food))
    }

    @Test
    fun givenAccountDoesntExists_whenGet_shouldReturnNull() {

        // Given
        every { repository.findByIdOrNull("food") } returns null

        // When
        val result = chartOfAccounts.get("food")

        // Then
        verify(exactly = 1){ repository.findByIdOrNull("food") }
        assertThat(result, nullValue())
    }

    @Test
    fun givenAccountDoesNotExists_whenAdd_shouldReturnAccount() {
        // Given
        val food = PersonalAccount("food", "food", AccountGroup.Expenses)
        every { repository.existsById("food") } returns false
        every { repository.save(any()) } returns food

        // When
        val result = chartOfAccounts.add(food)

        // Then
        verify(exactly = 1){ repository.save(food) }
        assertThat(result, equalTo(food))
    }

    @Test
    fun givenAccountExists_whenAdd_shouldThrow() {
        // Given
        val food = PersonalAccount("food", "food", AccountGroup.Expenses)
        every { repository.existsById("food") } returns true

        // Then
        val exception = assertThrows(AccountExistException::class.java){
            chartOfAccounts.add(food)
        }
        verify(exactly = 0){ repository.save(food) }
    }

    @Test
    fun givenAccountIsNotValid_whenAdd_shouldThrow() {
        // Given
        val food = PersonalAccount("food", "food", AccountGroup.Assets)
        every { repository.existsById("food") } returns false

        // Then
        val exception = assertThrows(GroupAccountingException::class.java){
            chartOfAccounts.add(food)
        }
        verify(exactly = 0){ repository.save(food) }
        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountCurrencyMissing::class.java)))
        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountOpenBalMissing::class.java)))
        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountOpenDateMissing::class.java)))
    }

    @Test
    fun givenAccountIsNotUsed_whenUpdate_shouldReturnAccount() {
        // Given
        val food = PersonalAccount("food", "Food", AccountGroup.Expenses)
        every { repository.existsById("food") } returns true
        every { repository.save(any()) } returns food
        every { generalLedger.journalExistsForAccount("food") } returns false

        // When
        val result = chartOfAccounts.update(food)

        // Then
        verify(exactly = 1){ repository.save(food) }
        assertThat(result, equalTo(food))
    }

    @Test
    fun givenAccountDoesNotExist_whenUpdate_shouldThrow() {
        // Given
        val food = PersonalAccount("food", "Food", AccountGroup.Expenses)
        every { repository.existsById("food") } returns false

        // Then
        val exception = assertThrows(NoSuchElementException::class.java){
            chartOfAccounts.update(food)
        }
        verify(exactly = 0){ repository.save(food) }
    }

    @Test
    fun givenAccountIsUsed_whenUpdate_shouldReturnAccount() {
        // Given
        val food = PersonalAccount("food", "Food", AccountGroup.Expenses)
        every { repository.existsById("food") } returns true
        every { generalLedger.journalExistsForAccount("food") } returns true

        // Then
        val exception = assertThrows(AccountAlreadyInUseException::class.java){
            chartOfAccounts.update(food)
        }
        verify(exactly = 0){ repository.save(food) }
    }

    @Test
    fun givenAccountIsNotValid_whenUpdate_shouldThrow() {
        // Given
        val food = PersonalAccount("food", "Food", AccountGroup.Assets)

        // Then
        val exception = assertThrows(GroupAccountingException::class.java){
            chartOfAccounts.update(food)
        }
        verify(exactly = 0){ repository.save(food) }
        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountCurrencyMissing::class.java)))
        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountOpenBalMissing::class.java)))
        assertThat(exception.exceptionSet, hasItem(isA(BalanceSheetAccountOpenDateMissing::class.java)))
    }

    @Test
    fun givenAccountIsNotUsed_whenDelete_shouldBeOk() {
        // Given
        val food = PersonalAccount("food", "Food", AccountGroup.Expenses)
        every { repository.existsById("food") } returns true
        every { generalLedger.journalExistsForAccount("food") } returns false
        every { repository.deleteById("food") } returns Unit

        // When
        val result = chartOfAccounts.delete("food")

        // Then
        verify(exactly = 1){ repository.deleteById("food") }
    }

    @Test
    fun givenAccountDoesNotExist_whenDelete_shouldThrow() {
        // Given
        val food = PersonalAccount("food", "Food", AccountGroup.Expenses)
        every { repository.existsById("food") } returns false

        // Then
        val exception = assertThrows(NoSuchElementException::class.java){
            chartOfAccounts.delete("food")
        }
        verify(exactly = 0){ repository.deleteById("food") }
    }

    @Test
    fun givenAccountIsUsed_whenDelete_shouldThrow() {
        // Given
        val food = PersonalAccount("food", "Food", AccountGroup.Expenses)
        every { repository.existsById("food") } returns true
        every { generalLedger.journalExistsForAccount("food") } returns true

        // Then
        val exception = assertThrows(AccountAlreadyInUseException::class.java){
            chartOfAccounts.delete("food")
        }
        verify(exactly = 0){ repository.deleteById("food") }
    }

    @Test
    fun givenAccountExists_whenGetAccountByGroup_shouldReturnAccountsCorrectly() {
        // Given
        val food = PersonalAccount("food", "Food", AccountGroup.Expenses)
        every { repository.findByGroupIn(any()) } returns listOf(food)

        // When
        val result = chartOfAccounts.getAccountsByGroup(AccountGroup.Expenses)

        // Then
        assertThat(result, hasItem(food))
        verify(exactly = 1) { repository.findByGroupIn(any()) }
    }

    @Test
    fun givenAccountExists_whenGetAccountByGroupAndCurrency_shouldReturnAccountsCorrectly() {
        // Given
        val cash = PersonalAccount("cash", "cash", AccountGroup.Assets, Currency.getInstance("SGD"), BigDecimal("100"), LocalDate.now())
        every { repository.findByCurrencyAndGroupIn(Currency.getInstance("SGD"), any()) } returns listOf(cash)

        // When
        val result = chartOfAccounts.getAccountsByGroupAndCurrency(AccountGroup.Assets, Currency.getInstance("SGD"))

        // Then
        assertThat(result, hasItem(cash))
        verify(exactly = 1) { repository.findByCurrencyAndGroupIn(any(), any()) }
    }

    @Test
    fun getBalanceSheetAccounts() {
        // Given
        val cash = PersonalAccount("cash", "cash", AccountGroup.Assets, Currency.getInstance("SGD"), BigDecimal("100"), LocalDate.now())
        every { repository.findByGroupIn(any()) } returns listOf(cash)

        // When
        val result = chartOfAccounts.getBalanceSheetAccounts()

        // Then
        assertThat(result, hasItem(cash))
        verify(exactly = 1) { repository.findByGroupIn(any()) }
    }

    @Test
    fun getIncomeStatementAccounts() {
        // Given
        val food = PersonalAccount("food", "Food", AccountGroup.Expenses)
        every { repository.findByGroupIn(any()) } returns listOf(food)

        // When
        val result = chartOfAccounts.getIncomeStatementAccounts()

        // Then
        assertThat(result, hasItem(food))
        verify(exactly = 1) { repository.findByGroupIn(any()) }
    }
}