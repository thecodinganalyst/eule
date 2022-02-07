package com.hevlar.accounting.domain.exception

class BalanceSheetAccountOpenDateMissing: InvalidAccountException() {
    override val message: String
        get() = "Opening Date cannot be empty for Asset/Liabilities account"
}