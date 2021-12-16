package com.hevlar.eule.cucumber.features

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.hevlar.eule.controller.AccountController
import com.hevlar.eule.model.Account
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import org.hamcrest.MatcherAssert.assertThat
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

        When("the following accounts are added"){ table: DataTable ->
            accountResults = addAccountsFromDataTable(table)
        }

        Then("the following accounts are returned"){ table: DataTable ->
            val rows = table.asMaps()
            rows.mapIndexed { i, it ->
                val expected = mapper.convertValue(it, Account::class.java)
                val account = accountResults[i].getOrThrow()
                assertThat(expected, samePropertyValuesAs(account))
            }
        }

        Given("the following accounts already exist") { table: DataTable ->
            givenAccountList = addAccountsFromDataTable(table).mapNotNull {
                it.getOrNull()
            }
        }

        Then("HttpStatus {int} is expected") { statusCode: Int ->
            accountResults.any {
                it.isFailure &&
                it.exceptionOrNull() is ResponseStatusException &&
                (it.exceptionOrNull() as ResponseStatusException).status == HttpStatus.valueOf(statusCode)
            }
        }

        When("account list is requested") {
            listAccountsFromDatabase()
        }

        When("the account with id {string} is requested") { accountId: String ->
            val account = getAccountFromDatabase(accountId)
            accountResults.add(account)
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

    private fun listAccountsFromDatabase(): List<Account>{
        return accountController.listAccounts()
    }

    private fun getAccountsFromDataTable(table: DataTable): MutableList<Account>{
        return table.asMaps().map {
            mapper.convertValue(it, Account::class.java)
        }.toMutableList()
    }

    private fun addAccountsFromDataTable(table: DataTable): MutableList<Result<Account>> {
        return getAccountsFromDataTable(table).map {
            try {
                val account = accountController.createAccount(it)
                Result.success(account)
            }catch (t: Throwable){
                Result.failure(t)
            }
        }.toMutableList()
    }

}