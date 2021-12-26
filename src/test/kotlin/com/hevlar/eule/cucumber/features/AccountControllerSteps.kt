package com.hevlar.eule.cucumber.features

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.hevlar.eule.controller.AccountController
import com.hevlar.eule.model.Account
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class AccountControllerSteps(@Autowired val accountController: AccountController): En {

    private var givenAccountList: List<Account> = listOf()
    private var accountResults: MutableList<Result<Account>> = mutableListOf()
    private var mapper: ObjectMapper = JsonMapper.builder().findAndAddModules().build()

    init {

        Before { _ ->
            accountResults = mutableListOf()
            givenAccountList = listOf()
        }

        Given("the following accounts already exist") { table: DataTable ->
            val tableAccounts = getAccountsFromDataTable(table)
            val accountIds = tableAccounts
                .mapNotNull { it.getOrNull() }
                .map { it.id }
            givenAccountList = accountIds
                .map { getAccountFromDatabase(it) }
                .mapNotNull { it.getOrNull() }
            assertThat(givenAccountList.size, equalTo(accountIds.size))
        }

        When("the following accounts are added"){ table: DataTable ->
            accountResults = addAccountsFromDataTable(table)
        }

        When("account list is requested") {
            accountResults = listAccountsFromDatabase()
        }

        When("the account with id {string} is requested") { accountId: String ->
            val account = getAccountFromDatabase(accountId)
            accountResults.add(account)
        }

        Then("the following accounts are returned"){ table: DataTable ->
            val rows = table.asMaps()
            rows.mapIndexed { i, it ->
                val expected = mapper.convertValue(it, Account::class.java)
                val account = accountResults[i].getOrThrow()
                assertThat(expected, samePropertyValuesAs(account))
            }
        }

        Then("HttpStatus {int} is expected") { statusCode: Int ->
            for (accountResult in accountResults) {
                assertThat(accountResult.isFailure, equalTo(true))
                val responseStatusException = accountResult.exceptionOrNull() as ResponseStatusException
                assertThat(responseStatusException.status, equalTo(HttpStatus.valueOf(statusCode)))
            }
        }

    }

    private fun getAccountFromDatabase(accountId: String): Result<Account>{
        return try {
            val account = accountController.getAccount(accountId)
            Result.success(account)
        }catch (t: Throwable){
            Result.failure(t)
        }
    }

    private fun listAccountsFromDatabase(): MutableList<Result<Account>>{
        return accountController.listAccounts().map { Result.success(it) }.toMutableList()
    }

    private fun getAccountsFromDataTable(table: DataTable): MutableList<Result<Account>>{
        return table.asMaps().map {
            try{
                Result.success(
                    mapper.convertValue(it, Account::class.java)
                )
            }catch(t: Throwable){
                Result.failure(t)
            }
        }.toMutableList()
    }

    private fun addAccountsFromDataTable(table: DataTable): MutableList<Result<Account>> {

        val accountResults = getAccountsFromDataTable(table)
        return accountResults.map {
            try {
                val account = accountController.createAccount(it.getOrThrow())
                Result.success(account)
            }catch (t: Throwable){
                Result.failure(t)
            }
        }.toMutableList()
    }

}