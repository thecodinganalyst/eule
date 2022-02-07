package com.hevlar.accounting.domain.exception

class BalanceSheetAccountOpenBalMissing: InvalidAccountException() {
    override val message: String
        get() = "Opening Balance cannot be empty for Asset/Liabilities account"
}