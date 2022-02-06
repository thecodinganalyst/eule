package com.hevlar.accounting.domain.model.account

enum class EntryType {
    Debit, Credit;

    companion object {
        fun oppositeOf(entryType: EntryType): EntryType{
            return if (entryType == Debit){
                Credit
            }else{
                Debit
            }
        }
    }
}