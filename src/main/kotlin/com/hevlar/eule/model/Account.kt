package com.hevlar.eule.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name="Accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("null")
open class Account(
    @Id
    @Column(name = "account_id")
    open val id: String,

    @Column(name = "name")
    open var name: String,

    @Column(name = "account_group")
    open var group: AccountGroup,

    @Column(name = "curr")
    open var currency: Currency,

    @Column(name = "openBal")
    open var openBal: BigDecimal,

    @Column(name = "openDate")
    open var openDate: Date)
{


}