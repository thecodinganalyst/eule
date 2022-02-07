package com.hevlar.accounting.domain.exception

class JournalTxDateAfterFY: InvalidJournalException() {
    override val message: String
        get() = "Journal Entry Tx Date cannot be after Financial Year end date"
}