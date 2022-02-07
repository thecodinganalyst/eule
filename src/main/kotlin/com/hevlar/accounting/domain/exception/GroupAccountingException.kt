package com.hevlar.accounting.domain.exception

class GroupAccountingException: AccountingException() {

    private val exceptionSet = mutableSetOf<AccountingException>()

    fun add(exception: AccountingException) {
        exceptionSet.add(exception)
    }

    fun throwIfNotEmpty() {
        if (exceptionSet.isNotEmpty()) throw this
    }

    override val message: String?
        get() = exceptionSet.fold(""){ acc, it -> acc + it.message + System.lineSeparator() }
}