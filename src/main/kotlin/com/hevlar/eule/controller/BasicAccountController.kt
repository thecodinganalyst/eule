package com.hevlar.eule.controller

import com.hevlar.eule.model.BasicAccount
import com.hevlar.eule.service.BasicAccountService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("accounts")
class BasicAccountController(val accountService: BasicAccountService) {

    @GetMapping
    @ResponseBody
    fun listAccounts(): List<BasicAccount>{
        return accountService.listAccounts()
    }

    @GetMapping(value = ["/{accountId}"])
    @ResponseBody
    fun getAccount(@PathVariable accountId: String): BasicAccount{
        return try{
            accountService.getAccount(accountId)
        }catch (e: Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account $accountId not found")
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@RequestBody account: BasicAccount): BasicAccount {
        if(accountService.existAccount(account.id))
            throw ResponseStatusException(HttpStatus.CONFLICT, "Account - ${account.id} already exists")

        return try{
            accountService.saveAccount(account)

        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account - ${account.id}")
        }
    }

    @PutMapping
    fun updateAccount(@RequestBody account: BasicAccount): BasicAccount? {
        return if (accountService.existAccount(account.id)){
            accountService.saveAccount(account)
        }else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account ${account.id} not found")
        }
    }

    @DeleteMapping(value = ["/{accountId}"])
    @ResponseStatus(HttpStatus.OK)
    fun deleteAccount(@PathVariable accountId: String): Boolean{
        return if (accountService.existAccount(accountId)){
            accountService.deleteAccount(accountId)
            true
        }else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Account $accountId not found")
        }
    }

}