package com.hevlar.accounting.domain.exception

class JournalTxDateBeforeFY: InvalidJournalException() {
    override val message: String
        get() = "Journal Entry Tx Date cannot be before Financial Year start date"
}