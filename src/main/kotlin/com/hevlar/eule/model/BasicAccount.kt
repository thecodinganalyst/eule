package com.hevlar.eule.model

import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name="Accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("null")
open class BasicAccount(
    @Id
    @Column(name = "account_id")
    open val id: String,

    @Column(name = "name")
    open var name: String,

    @Column(name = "account_group")
    open var group: AccountGroup,
    open var currency: Currency,
    open var openBal: BigDecimal,
    open var openDate: Date)
    : Account {


}