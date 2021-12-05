package com.hevlar.eule.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "JournalEntry")
open class JournalEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long,

    @Column(name = "txDate")
    var txDate: Date,

    @Column(name = "desc")
    var desc: String,

    @Column(name = "curr")
    var currency: Currency,

    @Column(name = "amount")
    var amount: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "debit", referencedColumnName = "account_id")
    var DebitAccount: Account,
)
{
}