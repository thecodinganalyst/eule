package com.hevlar.accounting.domain.exception

class AccountAlreadyInUseException(val account: String): AccountingException() {
    override val message: String?
        get() = "Account $account already in use"
}