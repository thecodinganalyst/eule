package com.hevlar.accounting.domain.exception

class BalanceSheetAccountOpenDateBeforeFY: InvalidAccountException() {
    override val message: String
        get() = "Opening Date cannot be before Financial Year start date"
}