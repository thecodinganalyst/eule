package com.hevlar.accounting.domain.model.journal

enum class Recurrence (val frequency: String) {
    Y ("Yearly"),
    M ("Monthly"),
    W ("Weekly"),
    D ("Daily")
}