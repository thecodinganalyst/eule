package com.hevlar.eule.controller

import com.hevlar.accounting.domain.exception.AccountAlreadyInUseException
import com.hevlar.accounting.domain.exception.AccountExistException
import com.hevlar.accounting.domain.exception.InvalidAccountException
import com.hevlar.accounting.domain.service.ChartOfAccounts
import com.hevlar.eule.model.PersonalAccount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("accounts")
class AccountController(val service: ChartOfAccounts<String, PersonalAccount>) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping
    @ResponseBody
    fun listAccounts(): Collection<PersonalAccount>{
        return service.list().map { it }
    }

    @GetMapping(value = ["/{accountId}"])
    @ResponseBody
    fun getAccount(@PathVariable accountId: String): PersonalAccount? {
        return service.get(accountId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@RequestBody account: PersonalAccount): PersonalAccount {
        return try {
            service.add(account)
        } catch (ae: AccountExistException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, ae.message)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PutMapping
    fun updateAccount(@RequestBody account: PersonalAccount): PersonalAccount {
        return try {
            service.update(account)
        } catch (nse: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, nse.message)
        } catch (aiu: AccountAlreadyInUseException) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, aiu.message)
        } catch (e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @DeleteMapping(value = ["/{accountId}"])
    @ResponseStatus(HttpStatus.OK)
    fun deleteAccount(@PathVariable accountId: String) {
        try {
            service.delete(accountId)
        } catch (nse: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, nse.message)
        } catch (aiu: AccountAlreadyInUseException) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, aiu.message)
        } catch (e: Exception){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

}