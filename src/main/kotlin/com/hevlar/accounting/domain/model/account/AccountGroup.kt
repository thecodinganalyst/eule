package com.hevlar.accounting.domain.model.account

enum class AccountGroup(val entryType: EntryType) {
    Assets(EntryType.Debit),
    Liabilities(EntryType.Credit),
    Revenue(EntryType.Credit),
    Expenses(EntryType.Debit),
    Gains(EntryType.Credit),
    Loss(EntryType.Debit)
}