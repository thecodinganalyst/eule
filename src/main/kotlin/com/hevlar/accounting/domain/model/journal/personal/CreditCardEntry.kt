package com.hevlar.accounting.domain.model.journal.personal

import java.time.LocalDate

interface CreditCardEntry {
    val postDate: LocalDate?
    val debitStatementDate: LocalDate?
    val creditStatementDate: LocalDate?
}