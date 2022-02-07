package com.hevlar.accounting.domain.exception

class BalanceSheetAccountOpenDateAfterFY: InvalidAccountException() {
    override val message: String
        get() = "Opening Date cannot be after Financial Year end date"
}