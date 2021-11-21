package com.hevlar.eule.model

import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="Accounts")
@DiscriminatorValue("CreditCard")
class CreditCardAccount(
    @Id
    override val id: String,
    override var name: String,
    override var group: AccountGroup,
    override var currency: Currency,
    override var openBal: BigDecimal,
    override var openDate: Date,
    var statementDay: BigInteger,
): BasicAccount(id, name, group, currency, openBal, openDate) {

}