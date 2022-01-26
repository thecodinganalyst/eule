package com.hevlar.eule.model

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "JOURNAL")
open class JournalEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "JE_ID")
    open val id: Long,

    @Column(name = "TX_DATE")
    open var txDate: LocalDate,

    @Column(name = "DESC")
    open var description: String,

    @Column(name = "CURR")
    open var currency: Currency,

    @Column(name = "AMOUNT")
    open var amount: BigDecimal,

    @JoinColumn(name = "DEBIT", referencedColumnName = "ACC_ID")
    open var debitAccountId: String,

    @JoinColumn(name = "CREDIT", referencedColumnName = "ACC_ID")
    open var creditAccountId: String,

    @Column(name = "POST_DATE")
    open var postDate: LocalDate,

    @Column(name = "RECUR")
    open var recurrence: Recurrence?,
)
{
//    constructor(txDate: String, description: String, currency: String, amount: String, debitAccount: String, creditAccount: String, postDate: String, recurrence: String): this(
//        LocalDate.parse(txDate), description, Currency.getInstance(currency), BigDecimal(amount), debitAccount, creditAccount, LocalDate.parse(postDate), Recurrence.valueOf(recurrence)
//    )

}