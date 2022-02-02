package com.hevlar.accounting.domain.exception

class JournalBeforeClosedDateException: AccountingException() {
    override val message: String?
        get() = "Attempt to modify journal entry before closed date"
}