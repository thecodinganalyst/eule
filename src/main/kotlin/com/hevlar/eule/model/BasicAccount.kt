package com.hevlar.eule.model

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name="ACCOUNT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("null")
open class BasicAccount(
    @Id
    @Column(name = "ACC_ID")
    open val id: String,

    @Column(name = "NAME")
    open var name: String,

    @Column(name = "ACC_GRP")
    open var group: AccountGroup,

    @Column(name = "CURR")
    open var currency: Currency?,

    @Column(name = "OPEN_BAL")
    open var openBal: BigDecimal?,

    @Column(name = "OPEN_DATE")
    open var openDate: LocalDate?
)