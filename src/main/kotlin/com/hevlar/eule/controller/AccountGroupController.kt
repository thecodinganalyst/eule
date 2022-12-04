package com.hevlar.eule.controller

import com.hevlar.accounting.domain.model.account.EntryType
import com.hevlar.accounting.domain.service.AccountGroupService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("accountGroups")
class AccountGroupController(val accountGroupService: AccountGroupService) {

    @GetMapping
    fun getAccountGroups(@RequestParam entryType: String?): Collection<String>{
        return if(entryType == null){
            accountGroupService.getAccountGroups()
        }else if(entryType.uppercase() == EntryType.Debit.name.uppercase()){
            accountGroupService.getAccountGroups(EntryType.Debit)
        }else if(entryType.uppercase() == EntryType.Credit.name.uppercase()){
            accountGroupService.getAccountGroups(EntryType.Credit)
        }else{
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid entry type, try Debit or Credit")
        }
    }

}