package com.hevlar.eule.controller

import com.hevlar.eule.model.Account
import com.hevlar.eule.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("accounts")
class AccountController(val accountService: AccountService) {

    @GetMapping
    fun listAccounts(): List<Account>{
        return accountService.listAccounts()
    }

    @GetMapping(value = ["/{accountId}"])
    fun getAccount(@PathVariable accountId: String): Account{
        return try{
            accountService.getAccount(accountId)
        }catch (e: Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account $accountId not found")
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@RequestBody account: Account): Account {
        if(accountService.existAccount(account.id))
            throw ResponseStatusException(HttpStatus.CONFLICT, "Account - $account already exists")

        return try{
            accountService.saveAccount(account)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account - $account")
        }
    }

    @PutMapping
    fun updateAccount(@RequestBody account: Account): Account? {
        return if (accountService.existAccount(account.id)){
            accountService.saveAccount(account)
        }else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account ${account.id} not found")
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    fun deleteAccount(@RequestBody accountId: String): Boolean{
        return if (accountService.existAccount(accountId)){
            accountService.deleteAccount(accountId)
            true
        }else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account $accountId not found")
        }
    }

}