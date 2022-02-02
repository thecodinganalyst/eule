package com.hevlar.accounting.domain.exception

class AccountExistException(val name: String): AccountingException() {
    override val message: String?
        get() = "Account $name already exists"
}