package com.hevlar.eule.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
open class BasicJournalEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    var txDate: Date,
    var desc: String,
    var currency: Currency,
    var amount: BigDecimal,
    var DebitAccount: Account,
): JournalEntry {
}