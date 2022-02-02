package com.hevlar.accounting.domain.model.account.personal

interface CreditCardAccount {
    val statementDay: Int?
    val dueDay: Int?
}