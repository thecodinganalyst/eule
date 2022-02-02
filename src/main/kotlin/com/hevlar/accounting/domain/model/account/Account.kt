package com.hevlar.accounting.domain.model.account

import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

interface Account<A: Any> {
    val id: A
    val group: AccountGroup
    val currency: Currency?
    val openBal: BigDecimal?
    val openDate: LocalDate?
}
