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

    var givenAccountList: List<Account> = listOf()
    var addAccountResults: List<Result<Account>> = listOf()
    private var mapper: ObjectMapper = JsonMapper.builder().findAndAddModules().build()

    init {

        Before { _ ->
            addAccountResults = listOf()
        }

        When("the following accounts are added"){ table: DataTable ->
            addAccountResults = addAccountsFromDataTable(table)
        }

        Then("the following accounts are returned"){ table: DataTable ->
            val rows = table.asMaps()
            rows.mapIndexed { i, it ->
                val expected = mapper.convertValue(it, Account::class.java)
                assertThat(expected, samePropertyValuesAs(addAccountResults[i].getOrThrow()))
            }
        }

        Given("the following accounts already exist") { table: DataTable ->
            givenAccountList = addAccountsFromDataTable(table).mapNotNull {
                it.getOrNull()
            }
        }

        Then("HttpStatus {int} is expected") { statusCode: Int ->
            addAccountResults.any {
                it.isFailure &&
                        it.exceptionOrNull() is ResponseStatusException &&
                        (it.exceptionOrNull() as ResponseStatusException).status == HttpStatus.valueOf(statusCode)
            }
        }

    }

    private fun listAccountsFromDatabase(): List<Account>{
        return accountController.listAccounts()
    }

    private fun getAccountsFromDataTable(table: DataTable): List<Account>{
        return table.asMaps().map {
            mapper.convertValue(it, Account::class.java)
        }
    }

    private fun addAccountsFromDataTable(table: DataTable): List<Result<Account>> {
        val addedAccounts = getAccountsFromDataTable(table).map {
            try {
                Result.success(accountController.createAccount(it))
            }catch (t: Throwable){
                Result.failure(t)
            }
        }
        return addedAccounts
    }

}