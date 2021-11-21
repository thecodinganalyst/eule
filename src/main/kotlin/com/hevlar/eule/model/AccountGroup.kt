package com.hevlar.eule.model

enum class AccountGroup(val entryType: EntryType) {
    Assets(EntryType.Debit),
    Liabilities(EntryType.Credit),
    Revenue(EntryType.Debit),
    Expenses(EntryType.Debit),
    Gains(EntryType.Debit),
    Loss(EntryType.Credit)
}