package com.hevlar.eule.service

import com.hevlar.eule.model.Account
import com.hevlar.eule.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService (val accountRepository: AccountRepository) {

    fun saveAccount(account: Account): Account{
        return accountRepository.save(account)
    }

    fun deleteAccount(accountId: String){
        return accountRepository.deleteById(accountId)
    }

    fun getAccount(accountId: String): Account{
        return accountRepository.findById(accountId).orElseThrow()
    }

    fun listAccounts(): List<Account>{
        return accountRepository.findAll()
    }

    fun existAccount(accountId: String): Boolean {
        return accountRepository.existsById(accountId);
    }

}