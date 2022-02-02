package com.hevlar.eule.model

import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.model.journal.personal.CreditCardEntry
import com.hevlar.accounting.domain.model.journal.Recurrence
import com.hevlar.accounting.domain.model.journal.RecurringEntry
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "PER_JOURNAL")
open class PersonalEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "JE_ID")
    override val id: Long,

    @Column(name = "TX_DATE")
    override var txDate: LocalDate,

    @Column(name = "DESC")
    override var description: String,

    @Column(name = "CURR")
    override var currency: Currency,

    @Column(name = "AMOUNT")
    override var amount: BigDecimal,

    @JoinColumn(name = "DEBIT", referencedColumnName = "ACC_ID")
    override var debitAccountId: String,

    @JoinColumn(name = "CREDIT", referencedColumnName = "ACC_ID")
    override var creditAccountId: String,

    @Column(name = "POST_DATE")
    override var postDate: LocalDate,

    @Column(name = "RECUR")
    override var recurrence: Recurrence?,

    @Column(name = "DEBIT_STMT_DATE")
    override var debitStatementDate: LocalDate?,

    @Column(name = "CREDIT_STMT_DATE")
    override var creditStatementDate: LocalDate?,


    ): JournalEntry<Long, String>, RecurringEntry, CreditCardEntry