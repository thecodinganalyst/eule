package com.hevlar.eule.model

import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.account.AccountGroup
import com.hevlar.accounting.domain.model.account.personal.CreditCardAccount
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="PER_ACCOUNT")
open class PersonalAccount(
    @Id
    @Column(name = "ACC_ID")
    override val id: String,

    @Column(name = "NAME")
    open var name: String,

    @Column(name = "ACC_GRP")
    override var group: AccountGroup,

    @Column(name = "CURR")
    override var currency: Currency?,

    @Column(name = "OPEN_BAL")
    override var openBal: BigDecimal?,

    @Column(name = "OPEN_DATE")
    override var openDate: LocalDate?,

    @Column(name = "STMT_DAY")
    override var statementDay: Int?,

    @Column(name = "DUE_DAY")
    override var dueDay: Int?

): Account<String>, CreditCardAccount{
    constructor(id: String, name: String, group: AccountGroup)
            : this(id, name, group, null, null, null, null, null)

    constructor(id: String, name: String, group: AccountGroup, currency: Currency?, openBal: BigDecimal?, openDate: LocalDate?)
            : this(id, name, group, currency, openBal, openDate, null, null)
}