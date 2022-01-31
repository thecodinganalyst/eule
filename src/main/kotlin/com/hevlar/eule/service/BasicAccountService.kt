package com.hevlar.eule.service

import com.hevlar.eule.model.BasicAccount
import com.hevlar.eule.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class BasicAccountService (val accountRepository: AccountRepository) {

    fun saveAccount(account: BasicAccount): BasicAccount{
        return accountRepository.save(account)
    }

    fun deleteAccount(accountId: String){
        return accountRepository.deleteById(accountId)
    }

    fun getAccount(accountId: String): BasicAccount{
        return accountRepository.findById(accountId).orElseThrow()
    }

    fun listAccounts(): List<BasicAccount>{
        return accountRepository.findAll()
    }

    fun existAccount(accountId: String): Boolean {
        return accountRepository.existsById(accountId);
    }

}