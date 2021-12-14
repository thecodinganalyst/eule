package com.hevlar.eule.cucumber.features

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.hevlar.eule.controller.AccountController
import com.hevlar.eule.cucumber.CucumberSpringConfiguration
import com.hevlar.eule.model.Account
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

class AccountControllerSteps(@Autowired val accountController: AccountController): En {

    init {
        var accounts: List<Account> = listOf()
        var mapper: ObjectMapper = JsonMapper.builder().findAndAddModules().build()

        When("the following accounts are added"){ table: DataTable ->
            val rows = table.asMaps()
            accounts = rows.map {
                val account = mapper.convertValue(it, Account::class.java)
                accountController.createAccount(account)
            }
        }

        Then("the following accounts are returned"){ table: DataTable ->
            val rows = table.asMaps()
            rows.mapIndexed { i, it ->
                val expected = mapper.convertValue(it, Account::class.java)
                assertThat(expected, samePropertyValuesAs(accounts[i]))
            }
        }

    }

}