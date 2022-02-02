package com.hevlar.accounting.domain.model.journal

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface JournalEntry<J: Any, A: Any> {
    val id: J
    val txDate: LocalDate
    val description: String
    val currency: Currency
    val amount: BigDecimal
    val debitAccountId: A
    val creditAccountId: A
}