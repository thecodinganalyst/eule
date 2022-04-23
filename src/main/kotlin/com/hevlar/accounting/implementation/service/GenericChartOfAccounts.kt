package com.hevlar.accounting.implementation.service

import com.hevlar.accounting.FinancialYear
import com.hevlar.accounting.domain.exception.*
import com.hevlar.accounting.domain.model.account.Account
import com.hevlar.accounting.domain.model.account.AccountGroup
import com.hevlar.accounting.domain.model.journal.JournalEntry
import com.hevlar.accounting.domain.service.ChartOfAccounts
import com.hevlar.accounting.domain.service.GeneralLedger
import com.hevlar.accounting.implementation.repository.GenericAccountRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.*

open class GenericChartOfAccounts<A :Any, J :Any, ACCOUNT: Account<A>, JOURNAL: JournalEntry<J, A>> (
    private val repository: GenericAccountRepository<A, ACCOUNT>,
    private val generalLedger: GeneralLedger<A, J, ACCOUNT, JOURNAL>,
    private val financialYear: FinancialYear
) : ChartOfAccounts<A, ACCOUNT> {

    override fun validate(account: ACCOUNT) {
        val collector = GroupAccountingException()
        if (account.group == AccountGroup.Assets || account.group == AccountGroup.Liabilities){
            if (account.openDate == null) collector.add(BalanceSheetAccountOpenDateMissing())
            if (account.openBal == null) collector.add(BalanceSheetAccountOpenBalMissing())
            if (account.currency == null) collector.add(BalanceSheetAccountCurrencyMissing())
            if(account.openDate != null){
                if (financialYear.startDate != null){
                    if (account.openDate!!.isBefore(financialYear.startDate)) collector.add(BalanceSheetAccountOpenDateBeforeFY())
                }
                if (financialYear.endDate != null){
                    if (account.openDate!!.isAfter(financialYear.endDate)) collector.add(BalanceSheetAccountOpenDateAfterFY())
                }
            }
        }

        collector.throwIfNotEmpty()
    }

    override fun exists(accountId: A): Boolean {
        return repository.existsById(accountId)
    }

    override fun list(): Collection<ACCOUNT> {
        return repository.findAll()
    }

    override fun get(id: A): ACCOUNT? {
        return repository.findByIdOrNull(id)
    }

    override fun add(account: ACCOUNT): ACCOUNT {
        validate(account)
        if (exists(account.id)) throw AccountExistException(account.id.toString())

        return repository.save(account)
    }

    override fun update(account: ACCOUNT): ACCOUNT {
        validate(account)
        if (!exists(account.id)) throw NoSuchElementException("Account with id ${account.id} does not exists")
        if (generalLedger.journalExistsForAccount(account.id)) throw AccountAlreadyInUseException(account.id.toString())

        return repository.save(account)
    }

    override fun delete(accountId: A) {
        if (!exists(accountId)) throw NoSuchElementException("Account with id $accountId does not exists")
        if (generalLedger.journalExistsForAccount(accountId)) throw AccountAlreadyInUseException(accountId.toString())

        repository.deleteById(accountId)
    }

    override fun getAccountsByGroup(accountGroup: AccountGroup): Collection<ACCOUNT> {
        return repository.findByGroupIn(listOf(accountGroup))
    }

    override fun getAccountsByGroupAndCurrency(accountGroup: AccountGroup, currency: Currency): Collection<ACCOUNT> {
        return repository.findByCurrencyAndGroupIn(currency, listOf(accountGroup))
    }

    override fun getBalanceSheetAccounts(): Collection<ACCOUNT> {
        return repository.findByGroupIn(listOf(AccountGroup.Assets, AccountGroup.Liabilities))
    }

    override fun getIncomeStatementAccounts(): Collection<ACCOUNT> {
        return repository.findByGroupIn(listOf(AccountGroup.Revenue, AccountGroup.Expenses, AccountGroup.Gains, AccountGroup.Loss))
    }
}