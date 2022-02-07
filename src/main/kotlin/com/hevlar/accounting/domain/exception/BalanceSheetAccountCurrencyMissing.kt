package com.hevlar.accounting.domain.exception

class BalanceSheetAccountCurrencyMissing: InvalidAccountException() {
    override val message: String
        get() = "Currency cannot be empty for Asset/Liabilities account"
}